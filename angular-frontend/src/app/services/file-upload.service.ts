import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const BASE_URL = "http://localhost:8080/api/files";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http: HttpClient) {
  }

  uploadFile(formData: FormData): Observable<any> {
    return this.http.post(BASE_URL, formData,);
  }

  getFileRegistryList(): Observable<any> {
    return this.http.get(BASE_URL + '/');
  }

  getBackendDownloadUrl(id: number): string {
    return BASE_URL + "/" + id;
  }
}
