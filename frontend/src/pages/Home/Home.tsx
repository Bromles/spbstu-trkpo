import { HospitalMap } from "@/components/HospitalMap/HospitalMap";
import {FormEvent, useCallback, useState} from "react";
import styles from "./Home.module.css";
import {DirectionSelection} from "@/components/Selection/DirectionSelection";
import {DistrictSelection} from "@/components/Selection/DistrictDelection";
import {HospitalSelection} from "@/components/Selection/HospitalSelection";
import {DoctorSelection} from "@/components/Selection/DoctorSelection";

export const Home = () => {
  return (
    <div className={styles.layout}>
      <Enrollment />
      <div className={styles.divider}></div>
      <Tracking />
    </div>
  );
};

const Enrollment = () => {
  const [selectedDistrictId, setSelectedDistrictId] = useState<number>(-1);
  const [selectedHospitalId, setSelectedHospitalId] = useState<number>(-1);
  const [selectedDirectionId, setSelectedDirectionId] = useState<number>(-1);
  const [selectedDoctorId, setSelectedDoctorId] = useState<number>(-1);

  const formHandler = useCallback( async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const sendData = async () => {
      let body;
      if (selectedDirectionId === -1 || selectedHospitalId === -1) {
        const errorSection = document.getElementById("errorSection");
        if (errorSection !== null) {
          errorSection.textContent = "Некорректные данные";
        }
        throw new Error('Некорректные данные');
      } else {
        const errorSection = document.getElementById("errorSection");
        if (errorSection !== null) {
          errorSection.textContent = null;
        }
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
      try {
        const response = await fetch("http://localhost:8082/v1/tracking", {
          method: "POST",
          headers: {
            'Content-Type': 'application/json',
          },
          body:body,
        });

        if (response.ok) {
          console.log("Отслеживание успешно начато!");
        } else {
          console.error("Ошибка при отправке данных.");
        }
      } catch (error) {
        console.error("Ошибка во время запроса:", error);
      }
    };

    sendData();
  }, [selectedDistrictId, selectedHospitalId, selectedDirectionId, selectedDoctorId]);

  return (
    <div>
      <div className={styles.enrollment_container}>
        <div>
          <h1>Описание сервиса</h1>
          <div className={styles.enrollment_section}>
            {"Супер крутой сервис, который поможет вам поймать талоны ко врачу!\u000A" +
            "Инструкция:\r" +
            "1. Выберите район, больницу и направление (врача опционально)\r" +
            "2. Нажмите кнопку \"Отслеживать\"\r" +
            "3. Ждите письмо на почту, с помощью которой регистрировались\r" +
            "4. Если вы хотите перестать отслеживать талон, нажмите кнопку \"Перестать отслеживать\" в правой части экрана"}
          </div>
        </div>
        <div className={styles.form_container}>
          <div className={styles.form_block}>
            <form onSubmit={formHandler} className={styles.form}>
              <DistrictSelection onChange={(districtId) => setSelectedDistrictId(districtId)} />
              <HospitalSelection selectedDistrictId={selectedDistrictId}
                  onHospitalChange={(hospitalId) => setSelectedHospitalId(hospitalId)} />
              <DirectionSelection selectedHospitalId={selectedHospitalId}
                  onDirectionChange={(directionId) => setSelectedDirectionId(directionId)} />
              <DoctorSelection selectedDirectionId={selectedDirectionId}
                  onDoctorChange={(doctorId) => setSelectedDoctorId(doctorId)} />
              <div defaultValue={selectedDoctorId}></div>
              <button type="submit">Отслеживать</button>
            </form>
            <div className={styles.form_section_error} id = "errorSection"></div>
          </div>
        </div>
        <HospitalMap />
      </div>
    </div>
  );
};

const Tracking = () => {
  return (
    <div className={styles.tracking_container}>
      <h1>Отслеживание</h1>
      <div className={styles.tracking_container_content}>
        {[0, 0, 0, 0, 0, 0, 0, 0, 0].map(() => (
          <TrackingItem />
        ))}
      </div>
    </div>
  );
};

const TrackingItem = () => {
  return (
    <div className={styles.trackingItem}>
      <h3>State</h3>
      <button type="button">Перестать отслеживать</button>
    </div>
  );
};
