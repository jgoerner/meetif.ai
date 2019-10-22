import React from "react";
import styles from "./PersonDetail.module.css";

export interface IPersonDetail {
    id: number,
    name: string,
    location: string
}

const PersonDetail = (props: IPersonDetail) => {
    return (
        <div className={styles.PersonDetailContainer}>
            <h1>{props.name}</h1>
            <p>Is based in {props.location}.</p>
        </div>
    );
};

export default PersonDetail;
