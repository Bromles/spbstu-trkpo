import { makeAutoObservable } from "mobx";

class Store {
  private _selectedHospitalId: number;
  private _selectedDistrictId: number;
  private _selectedDoctorId: number;
  private _selectedDirectionId: number;

  constructor() {
    this._selectedHospitalId = -1;
    this._selectedDistrictId = -1;
    this._selectedDoctorId = -1;
    this._selectedDirectionId = -1;

    makeAutoObservable(this);
  }

  get selectedHospitalId() {
    return this._selectedHospitalId;
  }

  set selectedHospitalId(selectedHospitalId: number) {
    this._selectedHospitalId = selectedHospitalId;
  }

  get selectedDistrictId() {
    return this._selectedDistrictId;
  }

  set selectedDistrictId(selectedDistrictId: number) {
    this._selectedDistrictId = selectedDistrictId;
  }

  get selectedDoctorId() {
    return this._selectedDoctorId;
  }

  set selectedDoctorId(selectedDoctorId: number) {
    this._selectedDoctorId = selectedDoctorId;
  }

  get selectedDirectionId() {
    return this._selectedDirectionId;
  }

  set selectedDirectionId(selectedDirectionId: number) {
    this._selectedDirectionId = selectedDirectionId;
  }
}

export const SelectionStore = new Store();
