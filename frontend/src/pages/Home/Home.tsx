import { HospitalMap } from "@/components/HospitalMap/HospitalMap";
import { FormEvent, useCallback, useEffect, useRef, useState } from "react";
import styles from "./Home.module.css";
import { DirectionSelection } from "@/components/Selection/DirectionSelection";
import { DistrictSelection } from "@/components/Selection/DistrictSelection";
import { HospitalSelection } from "@/components/Selection/HospitalSelection";
import { DoctorSelection } from "@/components/Selection/DoctorSelection";
import { Tracking } from "@/components/Tracking/Tracking";
import { getBackendUrl } from "@/utils/apiUtils";
import { GlobalStore } from "@/GlobalStore";
import { observer } from "mobx-react-lite";
import { addTracking, fetchHospitals, saveClient } from "./HomeApi";
import { useClientEmail, useClientId, useClientToken } from "@/utils/hooks";
import { SelectionStore } from "@/components/Selection/SelectionStore";

export const Home = () => {
  const [reloadTracking, setReloadTracking] = useState(false);

  const triggerReloadTracking = () => {
    setReloadTracking((prevState) => !prevState);
  };

  return (
    <div className={styles.layout}>
      <Enrollment onSubmit={triggerReloadTracking} />
      <div className={styles.divider}></div>
      <Tracking reload={reloadTracking} />
    </div>
  );
};

type EnrollmentProps = {
  onSubmit: () => void;
};

const Enrollment = observer(({ onSubmit }: EnrollmentProps) => {
  const clientToken = useClientToken();
  const clientId = useClientId();
  const clientEmail = useClientEmail();
  const errorSectionRef = useRef<HTMLDivElement | null>(null);
  const successSectionRef = useRef<HTMLDivElement | null>(null);

  const formHandler = useCallback(
    (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const backendUrl = getBackendUrl();
      const sendData = async () => {
        const errorSection = errorSectionRef.current;
        const successSection = successSectionRef.current;

        if (
          SelectionStore.selectedDirectionId === -1 ||
          SelectionStore.selectedHospitalId === -1
        ) {
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

        let body;
        if (SelectionStore.selectedDoctorId !== -1) {
          body = JSON.stringify({
            hospitalId: SelectionStore.selectedHospitalId,
            directionId: SelectionStore.selectedDirectionId,
            doctorId: SelectionStore.selectedDoctorId,
          });
        } else {
          body = JSON.stringify({
            hospitalId: SelectionStore.selectedHospitalId,
            directionId: SelectionStore.selectedDirectionId,
          });
        }

        const response = await addTracking(backendUrl, clientToken, body);

        if (response && response.ok) {
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

        onSubmit();
      };

      sendData();
    },
    [clientToken, onSubmit]
  );

  useEffect(() => {
    const backendUrl = getBackendUrl();
    saveClient(backendUrl, clientToken, clientId, clientEmail);
  });

  useEffect(() => {
    const backendURL = getBackendUrl();
    const fetchData = async () => {
      GlobalStore.hospitals = await fetchHospitals(backendURL, clientToken);
    };

    fetchData();
  }, [clientToken]);

  return (
    <div>
      <div className={styles.enrollment_container}>
        <div>
          <h1>Описание сервиса</h1>
          <div className={styles.enrollment_section}>
            Супер крутой сервис, который поможет вам поймать талоны ко врачу!
            <br />
            <h3>Инструкция:</h3>
            1. Выберите район, больницу и направление (врача опционально). Также
            у вас есть возможность выбрать больницу при помощи карты ниже
            <br />
            2. Нажмите кнопку <b>"Начать отслеживание"</b>
            <br />
            3. Ждите письмо на почту, с помощью которой регистрировались
            <br />
            4. Если вы хотите перестать отслеживать талон, нажмите кнопку{" "}
            <b>"Закончить отслеживание"</b> в правой части экрана
            <br />
          </div>
        </div>
        <div className={styles.form_container}>
          <div className={styles.form_block}>
            <form onSubmit={formHandler} className={styles.form}>
              <DistrictSelection
                onChange={(districtId) =>
                  (SelectionStore.selectedDistrictId = districtId)
                }
              />
              <HospitalSelection
                selectedDistrictId={SelectionStore.selectedDistrictId}
                onHospitalChange={(hospitalId) =>
                  (SelectionStore.selectedHospitalId = hospitalId)
                }
              />
              <DirectionSelection
                selectedHospitalId={SelectionStore.selectedHospitalId}
                onDirectionChange={(directionId) =>
                  (SelectionStore.selectedDirectionId = directionId)
                }
              />
              <DoctorSelection
                selectedDirectionId={SelectionStore.selectedDirectionId}
                selectedHospitalId={SelectionStore.selectedHospitalId}
                onDoctorChange={(doctorId) =>
                  (SelectionStore.selectedDoctorId = doctorId)
                }
              />
              <div defaultValue={SelectionStore.selectedDoctorId}></div>
              <button type="submit">Начать отслеживание</button>
            </form>
            <div
              ref={errorSectionRef}
              className={styles.form_section_error}
            ></div>
            <div
              ref={successSectionRef}
              className={styles.form_section_success}
            ></div>
          </div>
        </div>
        <HospitalMap />
      </div>
    </div>
  );
});
