import { HospitalMap } from "@/components/HospitalMap/HospitalMap";
import { FormEvent, useCallback, useEffect, useState } from "react";
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
  const [selectedDistrictId, setSelectedDistrictId] = useState<number>(-1);
  const [selectedHospitalId, setSelectedHospitalId] = useState<number>(-1);
  const [selectedDirectionId, setSelectedDirectionId] = useState<number>(-1);
  const [selectedDoctorId, setSelectedDoctorId] = useState<number>(-1);
  const clientToken = useClientToken();
  const clientId = useClientId();
  const clientEmail = useClientEmail();

  const formHandler = useCallback(
    (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const backendUrl = getBackendUrl();
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
            hospitalId: selectedHospitalId,
            directionId: selectedDirectionId,
            doctorId: selectedDoctorId,
          });
        } else {
          body = JSON.stringify({
            hospitalId: selectedHospitalId,
            directionId: selectedDirectionId,
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
    [
      selectedDirectionId,
      selectedHospitalId,
      selectedDoctorId,
      clientToken,
      onSubmit,
    ]
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
                onChange={(districtId) => setSelectedDistrictId(districtId)}
              />
              <HospitalSelection
                selectedDistrictId={selectedDistrictId}
                onHospitalChange={(hospitalId) =>
                  setSelectedHospitalId(hospitalId)
                }
              />
              <DirectionSelection
                selectedHospitalId={selectedHospitalId}
                onDirectionChange={(directionId) =>
                  setSelectedDirectionId(directionId)
                }
              />
              <DoctorSelection
                selectedDirectionId={selectedDirectionId}
                selectedHospitalId={selectedHospitalId}
                onDoctorChange={(doctorId) => setSelectedDoctorId(doctorId)}
              />
              <div defaultValue={selectedDoctorId}></div>
              <button type="submit">Начать отслеживание</button>
            </form>
            <div className={styles.form_section_error} id="errorSection"></div>
            <div
              className={styles.form_section_success}
              id="successSection"
            ></div>
          </div>
        </div>
        <HospitalMap />
      </div>
    </div>
  );
});
