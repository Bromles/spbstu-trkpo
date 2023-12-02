import { Map, Placemark, ZoomControl } from "@pbe/react-yandex-maps";
import { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import styles from "./HospitalMap.module.css";
import './HospitalMap.css';

export const HospitalMap = () => {
  const [activePortal, setActivePortal] = useState(false);

  return (
    <div className={styles.map_container}>
      <h2>Карта</h2>
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
          <ZoomControl />
          <Placemark
            defaultGeometry={[55.684758, 37.738521]}
            properties={{
              balloonContent: '<div id="hospital-balloon" class="hospital-baloon"></div>',
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
            <>
              <div className={styles.modal_container}>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Mollitia, atque! Aspernatur consectetur assumenda quibusdam, dolores rerum, cupiditate minima saepe velit enim amet suscipit totam quam nemo beatae impedit quae quisquam.</div>
            </>
          </BalloonPortal>
        )}
      </div>
    </div>
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
