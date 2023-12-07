import { makeAutoObservable } from "mobx";
import {
  Hospital,
  District,
  Doctor,
  Direction,
  TrackingItem,
} from "../utils/types";

export class GlobalStore {
  private _hospitals: Hospital[];
  private _districts: District[];
  private _doctors: Doctor[];
  private _directions: Direction[];
  private _trackingItems: TrackingItem[];

  constructor() {
    this._hospitals = [];
    this._districts = [];
    this._doctors = [];
    this._directions = [];
    this._trackingItems = [];

    makeAutoObservable(this);
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

  get trackingItems() {
    return this._trackingItems;
  }

  set trackingItems(trackingItems: TrackingItem[]) {
    this._trackingItems = trackingItems;
  }
}
