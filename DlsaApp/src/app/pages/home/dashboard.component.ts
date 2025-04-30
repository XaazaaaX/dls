import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule, DatePipe } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { PickListModule } from 'primeng/picklist';
import { MultiSelectModule } from 'primeng/multiselect';
import { DatePickerModule } from 'primeng/datepicker';
import { Group, GroupService } from '../../services/group.service';
import { Category, CategoryService } from '../../services/category.service';
import { Booking, BookingDto, BookingService } from '../../services/booking.service';
import { Member, MemberService } from '../../services/member.service';
import { Action, ActionService } from '../../services/action.service';
import { CourseOfYear, EvaluationService, Year } from '../../services/evaluation.service';
import { MenuModule } from 'primeng/menu';
import { AnnualServiceHours, MemberCount, MonthlyServiceHours, SectorsWithDlsFromYear, StatisticsService, TopDlsMember } from '../../services/statistics.service';
import { FluidModule } from 'primeng/fluid';
import { ChartModule } from 'primeng/chart';
import { TreeModule } from 'primeng/tree';

interface RadarSelectOption {
    name: string;
    code: string;
}

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        TextareaModule,
        SelectModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        TagModule,
        InputIconModule,
        IconFieldModule,
        ConfirmDialogModule,
        ToggleSwitchModule,
        ReactiveFormsModule,
        PickListModule,
        MultiSelectModule,
        DatePickerModule,
        MenuModule,
        ChartModule,
        FluidModule,
        TreeModule
    ],
    templateUrl: `./dashboard.component.html`,
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class DashboardComponent {

    products!: Object[];


    menu = null;

    items = [
        { label: 'Add New', icon: 'pi pi-fw pi-plus' },
        { label: 'Remove', icon: 'pi pi-fw pi-trash' }
    ];


    //-----------------

    memberCount: MemberCount = {};
    topDlsMembers: TopDlsMember[] = [{}];

    lineData: any;
    lineOptions: any;

    barData: any;
    barOptions: any;

    radarData: any;
    radarOptions: any;




    radarSelectOptions: RadarSelectOption[] | undefined;
    radarSelected: RadarSelectOption = { name: 'Bereiche', code: 'sector' };
    //------------


    constructor(
        private messageService: MessageService,
        private statisticsService: StatisticsService,
    ) { }

    ngOnInit() {
        this.loadMembercount();
        this.loadAnnualServiceHours();
        this.loadMonthlyServiceHours();
        this.loadTopDlsMember();
        this.loadSectorsWithDlsFromYear(this.radarSelected.code);

        this.radarSelectOptions = [
            { name: 'Bereiche', code: 'sector' },
            { name: 'Funktionsgruppen', code: 'group' },
            { name: 'Sparten', code: 'category' },
        ];
    }

    reloadRadarChart(option: RadarSelectOption){
        this.loadSectorsWithDlsFromYear(option.code);
    }

    loadMembercount() {
        this.statisticsService.getMemberCount().subscribe({
            next: (data) => {
                this.memberCount = data;
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadAnnualServiceHours() {
        this.statisticsService.getAnnualServiceHours().subscribe({
            next: (data) => {
                this.setupAnnualServiceHoursChart(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadMonthlyServiceHours() {
        this.statisticsService.getMonthlyServiceHours().subscribe({
            next: (data) => {
                this.setupMonthlyServiceHours(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadTopDlsMember() {
        this.statisticsService.getTopDlsMember().subscribe({
            next: (data) => {
                this.topDlsMembers = data;
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadSectorsWithDlsFromYear(code: string) {
        this.statisticsService.getSelectedWithDlsFromYear(code).subscribe({
            next: (data) => {
                this.setupSectorsWithDlsFromYear(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    setupAnnualServiceHoursChart(annualServiceHours: AnnualServiceHours) {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

        this.barData = {
            labels: annualServiceHours.labels,
            datasets: [
                {
                    label: 'DLS',
                    backgroundColor: documentStyle.getPropertyValue('--p-green-400'),
                    borderColor: documentStyle.getPropertyValue('--p-green-400'),
                    data: annualServiceHours.data
                }
            ]
        };

        this.barOptions = {
            maintainAspectRatio: false,
            aspectRatio: 0.8,
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: textColorSecondary,
                        font: {
                            weight: 500
                        }
                    },
                    grid: {
                        display: false,
                        drawBorder: false
                    }
                },
                y: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
    }

    setupMonthlyServiceHours(monthlyServiceHours: MonthlyServiceHours[]) {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

        this.lineData = {
            labels: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
            datasets: [
                {
                    label: monthlyServiceHours[0].label,
                    data: monthlyServiceHours[0].data,
                    fill: false,
                    backgroundColor: documentStyle.getPropertyValue('--p-blue-400'),
                    borderColor: documentStyle.getPropertyValue('--p-blue-400'),
                    tension: 0.4
                },
                {
                    label: monthlyServiceHours[1].label,
                    data: monthlyServiceHours[1].data,
                    fill: false,
                    backgroundColor: documentStyle.getPropertyValue('--p-orange-400'),
                    borderColor: documentStyle.getPropertyValue('--p-orange-400'),
                    tension: 0.4
                },
                {
                    label: monthlyServiceHours[2].label,
                    data: monthlyServiceHours[2].data,
                    fill: false,
                    backgroundColor: documentStyle.getPropertyValue('--p-green-400'),
                    borderColor: documentStyle.getPropertyValue('--p-green-400'),
                    tension: 0.4
                }
            ]
        };

        this.lineOptions = {
            maintainAspectRatio: false,
            aspectRatio: 0.8,
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                },
                y: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
    }

    setupSectorsWithDlsFromYear(sectorsWithDlsFromYear: SectorsWithDlsFromYear) {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

        const colorPalette = [
            documentStyle.getPropertyValue('--p-indigo-400'),
            documentStyle.getPropertyValue('--p-purple-400'),
            documentStyle.getPropertyValue('--p-blue-400'),
            documentStyle.getPropertyValue('--p-red-400'),
            documentStyle.getPropertyValue('--p-green-400'),
            documentStyle.getPropertyValue('--p-orange-400'),
            documentStyle.getPropertyValue('--p-teal-400')
        ];

        sectorsWithDlsFromYear.body!.forEach((apiDataset, index) => {
            const colorIndex = index % colorPalette.length;
            const datasetColor = colorPalette[colorIndex];

            apiDataset.borderColor = datasetColor;
            apiDataset.pointBackgroundColor = datasetColor;
            apiDataset.pointBorderColor = datasetColor;
            apiDataset.pointHoverBackgroundColor = textColor;
            apiDataset.pointHoverBorderColor = datasetColor;
        });

        this.radarData = {
            labels: sectorsWithDlsFromYear.labels,
            datasets: sectorsWithDlsFromYear.body
        };

        this.radarOptions = {
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            },
            scales: {
                r: {
                    pointLabels: {
                        color: textColor
                    },
                    grid: {
                        color: surfaceBorder
                    }
                }
            }
        };
    }
}
