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
      <div className={styles.enrollment_container}>
        <div>
          <h1>Описание сервиса</h1>
          <section className={styles.enrollment_section}>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Et saepe
            repudiandae id labore quam sunt magnam natus eos alias quod, maxime
            corrupti voluptas voluptate perferendis asperiores placeat quas
            aspernatur voluptatum!
          </section>
        </div>
        <div className={styles.form_container}>
          <div className={styles.form_block}>
            <form onSubmit={formHander} className={styles.form}>
              <div className={styles.form_section}>
                <label htmlFor="district" className={styles.label}>
                  Район:
                </label>
                <select name="district">
                  <option selected value="-1">
                    Выберите район
                  </option>
                  <option value="1">Выборгский</option>
                </select>
              </div>
              <div className={styles.form_section}>
                <label htmlFor="hospital" className={styles.label}>
                  Больница:
                </label>
                <select name="hospital">
                  <option selected value="-1">
                    Выберите учреждение
                  </option>
                  <option value="1">Боткина</option>
                </select>
              </div>
              <div className={styles.form_section}>
                <label htmlFor="direction" className={styles.label}>
                  Направление:
                </label>
                <select name="direction">
                  <option selected value="-1">
                    Выберите направление
                  </option>
                  <option value="1">Терапевт</option>
                </select>
              </div>
              <div className={styles.form_section}>
                <label htmlFor="doctor" className={styles.label}>
                  Доктор:
                </label>
                <select name="doctor">
                  <option selected value="-1">
                    Выберите врача
                  </option>
                  <option value="1">Айболит</option>
                </select>
              </div>
              <button type="submit">Отслеживать</button>
            </form>
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
