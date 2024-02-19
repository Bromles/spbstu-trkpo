import {vi} from 'vitest';
import {MockGlobalStore} from '@/mocks/stores';

const globalStore = new MockGlobalStore();
export const mockFetchDirections = vi.fn(() =>
    Promise.resolve({data: globalStore.directions}));

export const mockFetchDoctors = vi.fn((directionId: number) =>
    Promise.resolve({data: [globalStore.doctors.find(doctor => doctor.gorzdravId === directionId)]}));
