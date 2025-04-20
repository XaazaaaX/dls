import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Member } from './member.service';
import { Action } from './action.service';
import { HttpService } from './http.service';


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

    constructor(private httpService: HttpService) {}

    getAllBookings(): Observable<Booking[]> {
        return this.httpService.get<Booking[]>("journal/bookings");
    }

    createBooking(booking: BookingDto): Observable<Booking> {
        return this.httpService.post<Booking>("journal/booking", booking);
    }

    cancelBooking(id: number): Observable<Booking> {
        return this.httpService.delete<Booking>("journal/bookings/" + id);
    } 

}