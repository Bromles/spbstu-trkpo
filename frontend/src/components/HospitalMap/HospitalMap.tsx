/* eslint-disable react-hooks/exhaustive-deps */
import { Map, Placemark, ZoomControl } from "@pbe/react-yandex-maps";
import { useCallback, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import styles from "./HospitalMap.module.css";
import "./HospitalMap.css";

import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { observer } from "mobx-react-lite";
import { Hospital } from "@/utils/types";
import { fetchHospitals } from "@/pages/Home/HomeApi";
import { getBackendUrl } from "@/utils/apiUtils";
import { autorun } from "mobx";
import { HospitalMapStore } from "./HospitalMapStore";

export const HospitalMap = observer(() => {
  const [filteredHospitals, setFilteredHospitals] = useState<Hospital[]>([]);
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  useEffect(() => {
    const backendUrl = getBackendUrl();

    const fetchData = async () => {
      globalStore.hospitals = await fetchHospitals(backendUrl, clientToken);
    };

    fetchData();
  }, [clientToken]);

  useEffect(
    () =>
      autorun(() => {
        const backendUrl = getBackendUrl();

        const fetchData = async () => {
          const data = await fetchHospitals(backendUrl, clientToken);
          const filtered = data.filter(
            (hospital: Hospital) =>
              selectionStore.selectedDistrictId === hospital.districtId
          );
          setFilteredHospitals(filtered);
        };

        if (selectionStore.selectedDistrictId !== -1) {
          fetchData();
        } else {
          setFilteredHospitals(globalStore.hospitals);
        }
      }),
    [clientToken]
  );

  return (
    <div className={styles.map_container}>
      <h2>Карта</h2>
      <div>
        <Map
          defaultState={{
            center: [59.938659, 30.314457],
            zoom: 11,
            controls: [],
          }}
          modules={[
            "control.ZoomControl",
            "geoObject.addon.balloon",
            "geoObject.addon.hint",
          ]}
          className={styles.map}
        >
          <ZoomControl />
          {filteredHospitals.map((hospital) => (
            <BalloonWrapper key={hospital.id} hospital={hospital} />
          ))}
        </Map>
        {HospitalMapStore.activePortal && (
          <BalloonPortal>
            <BalloonContent />
          </BalloonPortal>
        )}
      </div>
    </div>
  );
});
HospitalMap.displayName = "HospitalMap";

const BalloonWrapper = observer(({ hospital }: { hospital: Hospital }) => {
  return (
    <Placemark
      defaultGeometry={[hospital.latitude, hospital.longitude]}
      properties={{
        balloonContent: `<div id="hospital-balloon-${hospital.id}" class="hospital-balloon"></div>`,
        hintContent: `${hospital.shortName}`,
      }}
      onClick={() => {
        setTimeout(() => {
          const closeButton = document.querySelector(
            'ymaps[class$="-balloon__close"]'
          );

          if (closeButton) {
            closeButton.addEventListener(
              "click",
              () => {
                HospitalMapStore.toggleActivePortal();
              },
              { once: true }
            );
          }
          HospitalMapStore.toggleActivePortal();
          HospitalMapStore.selectedPlacemarkId = hospital.id;
        }, 0);
      }}
    />
  );
});
BalloonWrapper.displayName = "BalloonWrapper";

const BalloonContent = observer(() => {
  const selectionStore = useSelectionStore();
  const globalStore = useGlobalStore();

  const hospital = globalStore.hospitals.find(
    (el) => el.id === HospitalMapStore.selectedPlacemarkId
  );

  const selectionHandler = useCallback(() => {
    if (hospital) {
      selectionStore.selectedDistrictId = hospital.districtId;
      selectionStore.selectedHospitalId = hospital.gorzdravId;
    }
  }, [hospital]);

  return (
    <>
      <div className={styles.modal_container}>
        {hospital?.fullName}
        <button onClick={selectionHandler}>Выбрать</button>
      </div>
    </>
  );
});
BalloonContent.displayName = "BalloonContent";

const BalloonPortal = observer(
  ({ children }: { children: React.ReactNode }) => {
    const mount = document.getElementById(
      `hospital-balloon-${HospitalMapStore.selectedPlacemarkId}`
    );
    const el = document.createElement("div");

    useEffect(() => {
      if (mount) {
        mount.appendChild(el);
      }

      return () => {
        if (mount) {
          mount.removeChild(el);
        }
      };
    }, [mount, el]);

    if (!mount) return null;

    return createPortal(children, el);
  }
);
BalloonPortal.displayName = "BalloonPortal";
