import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'
import {ApolloModule, APOLLO_OPTIONS} from 'apollo-angular'
import {HttpLink} from 'apollo-angular/http'
import {InMemoryCache} from '@apollo/client/core'

const GRAPHQL_HOST = 'http://localhost:8080/graphql';

@NgModule({
  imports: [BrowserModule, ApolloModule, HttpClientModule],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory(httpLink: HttpLink) {
        return {
          cache: new InMemoryCache(),
          link: httpLink.create({
            uri: GRAPHQL_HOST
          })
        }
      },
      deps: [HttpLink]
    }
  ]
})
export class ApolloClientModule {
}
