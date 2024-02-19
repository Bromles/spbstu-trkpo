import {makeObservable, observable} from "mobx";
import { Hospital, District, Doctor, Direction } from "@/utils/types";

export class GlobalStore {
  _hospitals: Hospital[] = [];
  _districts: District[] = [];
  _doctors: Doctor[] = [];
  _directions: Direction[] = [];
  _reloadTrackingToggle = false;

  constructor() {
    makeObservable(this, {
      _hospitals: observable,
      _districts: observable,
      _doctors: observable,
      _directions: observable,
      _reloadTrackingToggle: observable
    });
    // this._hospitals = [];
    // this._districts = [];
    // this._doctors = [];
    // this._directions = [];
    // this._reloadTrackingToggle = false;
    //
    // makeAutoObservable(this);
  }

  get hospitals() {
    return this._hospitals;
  }

  set hospitals(hospitals: Hospital[]) {
    this._hospitals = hospitals;
  }

  get districts() {
    return this._districts;
  }

  set districts(districts: District[]) {
    this._districts = districts;
  }

  get doctors() {
    return this._doctors;
  }

  set doctors(doctors: Doctor[]) {
    this._doctors = doctors;
  }

  get directions() {
    return this._directions;
  }

  set directions(directions: Direction[]) {
    this._directions = directions;
  }

  get trackingToggle() {
    return this._reloadTrackingToggle;
  }

  toggleReload() {
    this._reloadTrackingToggle = !this._reloadTrackingToggle;
  }
}
