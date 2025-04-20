import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Member } from './member.service';
import { Action } from './action.service';


export interface BookingDto {
    countDls?: number;
    comment?: string
    doneDate?: Date;
    memberId?: number;
    actionId?: number
}

export interface Booking {
    id?: number;
    countDls?: number;
    comment?: string
    doneDate?: Date;
    canceled?: boolean;
    bookingDate?: Date;
    member?: Member;
    action?: Action
}

@Injectable({
    providedIn: 'root'
})
export class JournalService {

    private apiUrl = 'http://127.0.0.1:5005'; // Ersetze mit deiner API-URL

    constructor(private http: HttpClient, private authService: AuthService) {
    }

    private getHttpHeader() {
        let authToken = this.authService.getToken();
        return {
            headers: new HttpHeaders({
                "Content-type": "application/json",
                Authorization: `Bearer ${authToken}`
            })
        };
    }

    getAllBookings(): Observable<Booking[]> {
        return this.http.get<Booking[]>(this.apiUrl + "/journal/bookings", this.getHttpHeader());
    }

    createBooking(booking: BookingDto): Observable<Booking> {
        return this.http.post<Booking>(this.apiUrl + "/journal/booking", booking, this.getHttpHeader());
    }

    cancelBooking(id: number): Observable<Booking> {
        return this.http.delete<Booking>(this.apiUrl + "/journal/bookings/" + id, this.getHttpHeader());
    } 

}