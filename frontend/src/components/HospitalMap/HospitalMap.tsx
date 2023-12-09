/* eslint-disable react-hooks/exhaustive-deps */
import { Map, Placemark, ZoomControl } from "@pbe/react-yandex-maps";
import { MouseEvent, useCallback, useEffect, useState } from "react";
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

export const HospitalMap = observer(() => {
  const [activePortal, setActivePortal] = useState(false);
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
            <Placemark
              key={hospital.id}
              defaultGeometry={[hospital.latitude, hospital.longitude]}
              properties={{
                balloonContent: `<div id="hospital-balloon-${hospital.id}" class="hospital-balloon"></div>`,
                hintContent: `${hospital.shortName}`,
              }}
              onClick={(e: MouseEvent<HTMLElement>) => {
                setTimeout(() => {
                  console.log(`react click target: ${e.target}`);
                  const selected = [
                    ...document.querySelectorAll(
                      'ymaps[class$="-balloon__close"]'
                    ),
                  ];
                  selected.forEach((el) => console.log(el));
                  const closeButton = selected[0];
                  if (closeButton !== null && closeButton !== undefined) {
                    closeButton.addEventListener("click", () => {
                      setActivePortal(false);
                    });
                  }

                  selectionStore.selectedHospitalId = hospital.id;
                  setActivePortal(true);
                }, 0);
              }}
            />
          ))}
        </Map>
        {activePortal && (
          <BalloonPortal
            elementId={`hospital-balloon-${selectionStore.selectedHospitalId}`}
          >
            <BalloonContent hospital={hospital!} />
          </BalloonPortal>
        )}
      </div>
    </div>
  );
});
HospitalMap.displayName = "HospitalMap";

const BalloonContent = observer(({ hospital }: { hospital: Hospital }) => {
  const selectionStore = useSelectionStore();

  const selectionHandler = useCallback(() => {
    selectionStore.selectedHospitalId = hospital!.id;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [hospital]);

  return (
    <>
      <div className={styles.modal_container}>
        {hospital.fullName}
        <button onClick={selectionHandler}>Выбрать</button>
      </div>
    </>
  );
});
BalloonContent.displayName = "BalloonContent";

const BalloonPortal = observer(
  ({
    children,
    elementId,
  }: {
    children: React.ReactNode;
    elementId: string;
  }) => {
    const mount = document.getElementById(elementId);
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
