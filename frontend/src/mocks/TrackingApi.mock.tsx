export function mockFetchTrackingItems() {
    return { json: () => Promise.resolve([{id: 1, directionId: 1, doctorId: -1,
        isFinished: false, clientId: "client-id", hospitalId: 1, hospitalGorzdravId: 1, hospitalFullName: "Городская больница №12 имени Пушкина"}])};
}

export function mockFetchHospitalInfo() {
    return { json: () => Promise.resolve({ directionName: "Терапевт", doctorName: null })};
}

export function mockDeleteTrackingItem() {
    return { json: () => Promise.resolve(void 0)};
}