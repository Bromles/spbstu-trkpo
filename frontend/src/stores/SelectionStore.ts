import {makeObservable, observable} from "mobx";

export class SelectionStore {
  _selectedHospitalId = -1;
  _selectedDistrictId = -1;
  _selectedDoctorId = -1;
  _selectedDirectionId = -1;

  constructor() {
    makeObservable(this, {
      _selectedHospitalId: observable,
      _selectedDistrictId: observable,
      _selectedDoctorId: observable,
      _selectedDirectionId: observable,
    });
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
