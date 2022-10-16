package org.lnu.timetable.service.faculty.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.dto.department.DepartmentItemDto;
import org.lnu.timetable.dto.faculty.FacultyContentDto;
import org.lnu.timetable.dto.faculty.FacultyDto;
import org.lnu.timetable.dto.faculty.FacultyItemDto;
import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.faculty.FacultyEntity;
import org.lnu.timetable.exception.NotFoundException;
import org.lnu.timetable.mapper.department.DepartmentMapper;
import org.lnu.timetable.mapper.faculty.FacultyMapper;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.lnu.timetable.service.faculty.FacultyService;
import org.lnu.timetable.service.image.FileStorageService;
import org.lnu.timetable.service.image.FileStorageService.FileProcessor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.lnu.timetable.controller.faculty.FacultyController.FACULTIES_ROOT_URI;
import static org.lnu.timetable.controller.faculty.FacultyController.FACULTY_LOGO_SUB_URI;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private static final String FACULTY_LOGO_FOLDER = "faculties/logos/";

    private static final NotFoundException FACULTY_LOGO_NOT_FOUND_EXCEPTION
            = new NotFoundException("Faculty logo not found!");

    private final FacultyRepository facultyRepository;

    private final DepartmentRepository departmentRepository;

    private final FacultyMapper facultyMapper;

    private final DepartmentMapper departmentMapper;

    private final FileStorageService fileStorageService;

    @Override
    public Mono<FacultyDto> create(FacultyContentDto facultyContentDto) {
        FacultyEntity facultyEntity = facultyMapper.toFaculty(facultyContentDto);
        return facultyRepository.create(facultyEntity).map(createdFaculty -> {
            FacultyDto facultyDto = facultyMapper.toFacultyDto(createdFaculty);
            return facultyDto;
        });
    }

    @Override
    public Flux<FacultyItemDto> findAllItems() {
        return facultyRepository.findAllItems().collectList().flatMap(faculties -> {
            List<FacultyItemDto> facultyItems = facultyMapper.toFacultyItems(faculties);

            List<FileProcessor> fileProcessors = facultyItems.stream().map(facultyItemDto -> {
                Long facultyId = facultyItemDto.getId();
                String facultyLogoFileName = getFacultyLogoFileName(facultyId);

                FileProcessor fileProcessor = new FileProcessor(facultyLogoFileName) {
                    @Override
                    public void process() {
                        String facultyLogoUri = getFacultyLogoUri(facultyId);
                        facultyItemDto.setFacultyLogoUri(facultyLogoUri);
                    }
                };

                return fileProcessor;
            }).collect(Collectors.toList());

            return fileStorageService.checkIfFilesExists(fileProcessors).thenReturn(facultyItems);
        }).flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<FacultyDto> findById(Long facultyId) {
        Mono<FacultyEntity> facultyMono = facultyRepository.findById(facultyId);
        Mono<List<DepartmentEntity>> departmentsMono = departmentRepository.findItemsByFacultyId(facultyId).collectList();

        return Mono.zip(facultyMono, departmentsMono).flatMap(tuple -> {
            FacultyEntity facultyEntity = tuple.getT1();
            List<DepartmentEntity> departments = tuple.getT2();

            FacultyDto facultyDto = facultyMapper.toFacultyDto(facultyEntity);

            List<DepartmentItemDto> departmentItemDtoList = departmentMapper.toDepartmentItemDtoList(departments);
            facultyDto.setDepartments(departmentItemDtoList);

            String facultyLogoFileName = getFacultyLogoFileName(facultyId);

            return fileStorageService.checkIfFileExists(facultyLogoFileName).doOnSuccess(facultyLogoExists -> {
                        if (facultyLogoExists) {
                            String facultyLogoUri = getFacultyLogoUri(facultyId);
                            facultyDto.setFacultyLogoUri(facultyLogoUri);
                        }
                    }
            ).thenReturn(facultyDto);
        });
    }

    @Override
    public Mono<Void> update(Long facultyId, FacultyContentDto facultyContentDto) {
        FacultyEntity facultyEntity = facultyMapper.toFaculty(facultyContentDto);
        facultyEntity.setId(facultyId);
        return facultyRepository.update(facultyEntity);
    }

    @Override
    public Mono<Void> remove(Long facultyId) {
        String facultyLogoFileName = getFacultyLogoFileName(facultyId);
        return facultyRepository.remove(facultyId)
                .then(fileStorageService.removeFileIfExists(facultyLogoFileName))
                .then();
    }

    @Override
    public Mono<Void> uploadFacultyLogo(Long facultyId, FilePart facultyLogoFilePart) {
        String facultyLogoFileName = getFacultyLogoFileName(facultyId);
        return fileStorageService.saveFile(facultyLogoFileName, facultyLogoFilePart);
    }

    @Override
    public Flux<DataBuffer> readFacultyLogo(Long facultyId) {
        String facultyLogoFileName = getFacultyLogoFileName(facultyId);
        return fileStorageService.readFile(facultyLogoFileName, FACULTY_LOGO_NOT_FOUND_EXCEPTION);
    }

    private String getFacultyLogoFileName(Long facultyId) {
        return FACULTY_LOGO_FOLDER + facultyId;
    }

    private String getFacultyLogoUri(Long facultyId) {
        return FACULTIES_ROOT_URI + "/" + facultyId + "/" + FACULTY_LOGO_SUB_URI;
    }
}
