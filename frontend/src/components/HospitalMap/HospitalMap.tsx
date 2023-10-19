import { Map, Placemark } from "@pbe/react-yandex-maps";
import { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import styles from "./HospitalMap.module.css";

export const HospitalMap = () => {
  const [activePortal, setActivePortal] = useState(false);

  return (
    <>
      <div>
        <Map
          defaultState={{
            center: [55.751574, 37.573856],
            zoom: 5,
            controls: [],
          }}
          modules={[
            "control.ZoomControl",
            "geoObject.addon.balloon",
            "geoObject.addon.hint",
          ]}
          className={styles.map}
        >
          <Placemark
            defaultGeometry={[55.684758, 37.738521]}
            properties={{
              balloonContent: '<div id="hospital-balloon"></div>',
              hintContent: "<b>Balloon hint</b>",
            }}
            onClick={() => {
              setTimeout(() => {
                setActivePortal(true);
              }, 0);
            }}
          />
        </Map>
        {activePortal && (
          <BalloonPortal elementId={"hospital-balloon"}>
            <>Hello from portal</>
          </BalloonPortal>
        )}
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
