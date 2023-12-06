import { Map, Placemark, ZoomControl } from "@pbe/react-yandex-maps";
import { MouseEvent, useCallback, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import styles from "./HospitalMap.module.css";
import "./HospitalMap.css";
import { useAuth } from "react-oidc-context";

import { getBackendUrl } from "@/utils/apiUtils";
import { Hospital, Direction, Doctor } from "@/utils/types";

export const HospitalMap = () => {
  const [activePortal, setActivePortal] = useState(false);
  const [selectedPlacemarkId, setSelectedPlacemarkId] = useState<number | null>(
    null
  );
  const [isEventSet, setIsEventSet] = useState(false);

  const getHospital = () => {
    if (selectedPlacemarkId && hospitals) {
      return hospitals[selectedPlacemarkId];
    }

    return null;
  };

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
          {hospitals?.map((hospital) => (
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
                  const closeButton = [
                    ...document.querySelectorAll(
                      'ymaps[class$="-balloon__close"]'
                    ),
                  ].forEach((el) => console.log(`selected target: ${el}`));
                  if (
                    closeButton !== null &&
                    closeButton !== undefined &&
                    !isEventSet
                  ) {
                    // closeButton.addEventListener('click', () => {
                    //   setSelectedPlacemarkId(null);
                    //   setActivePortal(false);
                    //   setIsEventSet(true);
                    // });
                  }

                  setSelectedPlacemarkId(hospital.id);
                  setActivePortal(true);
                }, 0);
              }}
            />
          ))}
        </Map>
        {activePortal && (
          <BalloonPortal elementId={`hospital-balloon-${selectedPlacemarkId}`}>
            <BalloonContent hospital={getHospital()} />
          </BalloonPortal>
        )}
      </div>
    </div>
  );
};

const BalloonContent = ({
  hospital,
  direction,
  doctor,
}: {
  hospital: Hospital | null;
  direction: Direction | null;
  doctor: Doctor | null;
}) => {
  const auth = useAuth();

  const startTrackingHandler = useCallback(() => {
    const backendURL = getBackendUrl();

    const sendData = async () => {
      let body;
      const errorSection = document.getElementById("errorSection");
      const successSection = document.getElementById("successSection");
      if (selectedDirectionId === -1 || selectedHospitalId === -1) {
        if (errorSection !== null) {
          errorSection.textContent = "Некорректные данные";
        }
        if (successSection !== null) {
          successSection.textContent = null;
        }
        throw new Error("Некорректные данные");
      } else if (errorSection !== null) {
        errorSection.textContent = null;
      }
      if (selectedDoctorId !== -1) {
        body = JSON.stringify({
          hospitalId: hospital.id,
          directionId: selectedDirectionId,
          doctorId: selectedDoctorId,
        });
      } else {
        body = JSON.stringify({
          hospitalId: selectedHospitalId,
          directionId: selectedDirectionId,
        });
      }
      try {
        const response = await fetch(`${backendURL}/v1/tracking`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${auth.user?.access_token}`,
          },
          body: body,
        });

        if (response.ok) {
          if (successSection !== null) {
            successSection.textContent = "Успех!";
          }
          console.log("Отслеживание успешно начато!");
        } else {
          if (errorSection !== null) {
            errorSection.textContent = "Ошибка при отправке данных.";
          }
          console.error("Ошибка при отправке данных.");
        }
      } catch (error) {
        console.error("Ошибка во время запроса:", error);
      }
    };

    sendData();
  }, [auth.user?.access_token]);

  return (
    <>
      <div className={styles.modal_container}>
        {hospital?.fullName}
        <button onClick={startTrackingHandler}>Начать отслеживание</button>
      </div>
    </>
  );
};

const BalloonPortal = ({
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
};
