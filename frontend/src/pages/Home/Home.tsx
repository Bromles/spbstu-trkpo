//import { HospitalMap } from "@/components/HospitalMap/HospitalMap";
import { FormEvent, useCallback, useEffect, useRef } from "react";
import styles from "./Home.module.css";
import { DirectionSelection } from "@/components/Selection/DirectionSelection";
import { DistrictSelection } from "@/components/Selection/DistrictSelection";
import { HospitalSelection } from "@/components/Selection/HospitalSelection";
import { DoctorSelection } from "@/components/Selection/DoctorSelection";
import { Tracking } from "@/components/Tracking/Tracking";
import { getBackendUrl } from "@/utils/apiUtils";
import { observer } from "mobx-react-lite";
import { addTracking, saveClient } from "./HomeApi";
import {
  useClientEmail,
  useClientId,
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { trace } from "mobx";

export const Home = () => {
  return (
    <div className={styles.layout}>
      <Enrollment />
      <div className={styles.divider}></div>
      <Tracking />
    </div>
  );
};

const Enrollment = observer(() => {
  trace();
  const clientToken = useClientToken();
  const clientId = useClientId();
  const clientEmail = useClientEmail();
  const errorSectionRef = useRef<HTMLDivElement | null>(null);
  const successSectionRef = useRef<HTMLDivElement | null>(null);
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const formHandler = useCallback(
    (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const backendUrl = getBackendUrl();

      const sendData = async () => {
        const errorSection = errorSectionRef.current;
        const successSection = successSectionRef.current;

        if (
          selectionStore.selectedDirectionId === -1 ||
          selectionStore.selectedHospitalId === -1
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
        if (selectionStore.selectedDoctorId !== -1) {
          body = JSON.stringify({
            hospitalId: selectionStore.selectedHospitalId,
            directionId: selectionStore.selectedDirectionId,
            doctorId: selectionStore.selectedDoctorId,
          });
        } else {
          body = JSON.stringify({
            hospitalId: selectionStore.selectedHospitalId,
            directionId: selectionStore.selectedDirectionId,
          });
        }

        const response = await addTracking(backendUrl, clientToken, body);

        if (response && response.ok) {
          if (successSection !== null) {
            successSection.textContent = "Успех!";
            setTimeout(() => (successSection.textContent = ""), 3000);
          }
          console.log("Отслеживание успешно начато!");
        } else {
          if (errorSection !== null) {
            errorSection.textContent = "Ошибка при отправке данных.";
            setTimeout(() => (errorSection.textContent = ""), 3000);
          }
          console.error("Ошибка при отправке данных.");
        }

        globalStore.toggleReload();
      };

      sendData();
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [
      clientToken,
      selectionStore.selectedDirectionId,
      selectionStore.selectedDoctorId,
      selectionStore.selectedHospitalId,
    ]
  );

  useEffect(() => {
    const backendUrl = getBackendUrl();
    saveClient(backendUrl, clientToken, clientId, clientEmail);
  }, [clientEmail, clientId, clientToken]);

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
              <DistrictSelection />
              <HospitalSelection />
              <DirectionSelection />
              <DoctorSelection />
              <div defaultValue={selectionStore.selectedDoctorId}></div>
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
        {/*<HospitalMap />*/}
      </div>
    </div>
  );
});
Enrollment.displayName = "Enrollment";
