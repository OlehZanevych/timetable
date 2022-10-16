import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HOST_URL} from "../../constants/Http";
import {UserCredentials} from '../../model/user/UserCredentials';
import {Observable} from 'rxjs';

const LOG_IN_URL = `${HOST_URL}/auth/sign-in`;

const REFRESH_TOKEN_URL = `${HOST_URL}/auth/refresh-token`;

// 15 seconds
const AUTH_TOKEN_ALLOWABLE_TIME_REMAINING = 15;

// 2 minutes
const AUTH_TOKEN_ALLOWABLE_LIFETIME = 120;

// 10 seconds
const TOKEN_SCHEDULED_CHECK_TIME = 10000;

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {
  private tokenCheckTimerId: any;

  constructor(private http: HttpClient) {
    this.setScheduleForTokenRefresh();
  }

  ngOnDestroy() {
    clearInterval(this.tokenCheckTimerId);
  }

  signIn(userCredentials: UserCredentials): Observable<void> {
    return this.http.post<void>(LOG_IN_URL, userCredentials);
  }

  logOut(): void {
    localStorage.clear();
  }

  refreshToken(): void {
    this.http.get<void>(REFRESH_TOKEN_URL).subscribe();
  }

  processAuthToken(authToken: string): void {
    const payload = this.retrieveAuthTokenPayload(authToken);
    localStorage.authToken = `Bearer ${authToken}`;
    localStorage.user = JSON.stringify(payload.user);
    this.saveAuthTokenCreationAndExpirationTime(payload);
  }

  getUserName(): string {
    const {firstName, lastName} = JSON.parse(localStorage.user);
    return `${firstName} ${lastName}`;
  }

  isSignedIn(): boolean {
    const {authToken, user, iat, exp} = localStorage;
    return this.isAuthDataExistAndNotExpired(authToken, user, iat, exp);
  }

  setScheduleForTokenRefresh(): void {
    this.tokenCheckTimerId = setInterval(() => this.checkAuthDataAndRefreshToken(),
      TOKEN_SCHEDULED_CHECK_TIME);
  }

  checkAuthDataAndRefreshToken(): void {
    const {authToken, user, iat, exp} = localStorage;
    if (this.isAuthDataExistAndNotExpired(authToken, user, iat, exp)
      && this.getCurrentTime() - iat > AUTH_TOKEN_ALLOWABLE_LIFETIME) {

      this.refreshToken();
    }
  }

  isAuthDataExistAndNotExpired(authToken: string, user: any, iat : number, exp : number): boolean {
    return authToken && user && iat && exp && exp > this.getCurrentTime();
  }

  retrieveAuthTokenPayload(authToken: string) {
    const payloadStartIndex = authToken.indexOf('.') + 1;
    const payloadEndIndex = authToken.indexOf('.', payloadStartIndex);
    const encodedPayload = authToken.substring(payloadStartIndex, payloadEndIndex);
    return JSON.parse(atob(encodedPayload));
  }

  saveAuthTokenCreationAndExpirationTime(payload : any): void {
    const {iat, exp} = payload;
    const lifeTime = exp - iat;
    const creationTime = this.getCurrentTime();
    const expirationTime = creationTime + lifeTime - AUTH_TOKEN_ALLOWABLE_TIME_REMAINING;

    localStorage.iat = creationTime;
    localStorage.exp = expirationTime;
  }

  getCurrentTime(): number {
    // Unix timestamp in seconds
    return (new Date().getTime() / 1000) >> 0;
  }
}
