import { HospitalMap } from "@/components/HospitalMap/HospitalMap";
import { FormEvent, useCallback } from "react";
import styles from "./Home.module.css";

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
  const formHander = useCallback((e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  }, []);

  return (
    <div>
      <h1>Описание сервиса</h1>
      <section>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Et saepe
        repudiandae id labore quam sunt magnam natus eos alias quod, maxime
        corrupti voluptas voluptate perferendis asperiores placeat quas
        aspernatur voluptatum!
      </section>
      <form onSubmit={formHander} className={styles.form}>
        <select name="district">
          <option selected value="-1">
            Выберите район
          </option>
          <option value="1">Выборгский</option>
        </select>
        <select name="hospital">
          <option selected value="-1">
            Выберите учреждение
          </option>
          <option value="1">Боткина</option>
        </select>
        <select name="doctor">
          <option selected value="-1">
            Выберите врача
          </option>
          <option value="1">Айболит</option>
        </select>
        <button type="submit">Отслеживать</button>
      </form>
      <HospitalMap />
    </div>
  );
};

const Tracking = () => {
  return (
    <div>
      <h1>Отслеживание</h1>
      <TrackingItem />
    </div>
  );
};

const TrackingItem = () => {
  return (
    <div className={styles.trackingItem}>
      <h3>State</h3>
      <button type="button">Stop tracking</button>
    </div>
  );
};
