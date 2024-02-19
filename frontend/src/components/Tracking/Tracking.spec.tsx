// import {afterEach, describe, expect, it, Mock, vi} from "vitest";
// import {MockGlobalStore} from "@/mocks/stores";
// import {useGlobalStore} from "@/utils/hooks";
// import {render, screen, waitFor} from "@testing-library/react";
// import {Tracking} from "@/components/Tracking/Tracking";
// import {deleteTrackingItem, fetchHospitalInfo, fetchTrackingItems} from "@/components/Tracking/TrackingApi";
// import userEvent from "@testing-library/user-event";
// import {mockDeleteTrackingItem, mockFetchHospitalInfo, mockFetchTrackingItems} from "@/mocks/TrackingApi.mock";
//
// global.fetch = vi.fn()
//
// vi.mock("@/utils/hooks", () => ({
//     useGlobalStore: vi.fn(),
//     useClientId: vi.fn().mockReturnValue("client-id"),
//     useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
// }));
//
// vi.mock("@/utils/apiUtils", () => ({
//     getBackendUrl: vi.fn().mockReturnValue("mock-url"),
// }));
//
// // vi.mock("@/components/TrackingApi", () => ({
// //     fetchTrackingItems: vi.fn(),//.mockReturnValue(Promise.resolve([{id: 1, directionId: 1, doctorId: -1,
// //        //isFinished: false, clientId: "client-id", hospitalId: 1, hospitalGorzdravId: 1, hospitalFullName: "Городская больница №12 имени Пушкина"}])),
// //     fetchHospitalInfo: vi.fn(),//.mockReturnValue(Promise.resolve({ directionName: "Терапевт", doctorName: null })),
// //     deleteTrackingItem: vi.fn(),//.mockReturnValue(Promise.resolve(void 0)),
// // }));
//
// describe("Tracking", () => {
//     let globalStore: MockGlobalStore;
//
//     beforeEach(() => {
//         globalStore = new MockGlobalStore();
//         (useGlobalStore as Mock).mockReturnValue(globalStore);
//
//         // (fetchTrackingItems as Mock).mockReturnValue(mockFetchTrackingItems);
//         // (fetchHospitalInfo as Mock).mockReturnValue(mockFetchHospitalInfo);
//         // (deleteTrackingItem as Mock).mockReturnValue(mockDeleteTrackingItem);
//     });
//
//     afterEach(() => {
//         vi.resetAllMocks();
//     });
//
//     it('renders without crashing', async () => {
//         try {
//             console.log("hi");
//             // fetch.mockResolvedValue(mockFetchTrackingItems());
//             render(<Tracking />);
//             await waitFor(() => {
//                 expect(screen.getByText('Отслеживание')).toBeTruthy();
//             });
//         } catch (e) {
//             console.log("error: " + e);
//         }
//     });
//
//     it('fetches tracking items on render', async () => {
//         render(<Tracking />);
//         await waitFor(() => {
//             expect(fetchTrackingItems).toHaveBeenCalledTimes(1);
//         });
//     });
//
//     it('deletes a tracking item when button is clicked', async () => {
//         vi.mocked(fetchTrackingItems).mockResolvedValueOnce([
//             {
//                 id: '1',
//                 hospitalFullName: 'Some Hospital',
//                 directionName: 'Some Direction',
//                 doctorName: 'Doctor Who',
//                 hospitalGorzdravId: 'some-id',
//                 directionId: 'some-direction-id',
//                 doctorId: 'some-doctor-id',
//             },
//         ]);
//         render(<Tracking />);
//         const deletionButton = await screen.findByText('Закончить отслеживание');
//         userEvent.click(deletionButton);
//
//         await waitFor(() => {
//             expect(deleteTrackingItem).toHaveBeenCalledTimes(1);
//         });
//     });
// });
