export type Direction = {
  id: number;
  countFreeTicket: number;
  name: string;
};

export type District = {
  id: number;
  gorzdravId: number;
  name: string;
};

export type Doctor = {
  gorzdravId: number;
  name: string;
};

export type Hospital = {
  id: number;
  gorzdravId: number;
  latitude: number;
  longitude: number;
  districtId: number;
  address: string;
  fullName: string;
  shortName: string;
  phone: string;
};

export type TrackingItem = {
  id: number;
  directionId: number;
  doctorId: number;
  isFinished: boolean;
  clientId: number;
  hospitalId: number;
  hospitalGorzdravId: number;
  hospitalFullName: string;
};
