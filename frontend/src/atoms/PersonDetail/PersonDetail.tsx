import React from "react";
import styles from "./PersonDetail.module.css";

export interface IPersonDetail {
    name: string,
    location: string,
    participatedEvents: number
}

const PersonDetail = (props: IPersonDetail) => {
    return (
        <div className={styles.PersonDetailContainer}>
            <h1>{props.name}</h1>
            <p>Is based in {props.location} and participated in {props.participatedEvents} events.</p>
        </div>
    );
};

export default PersonDetail;
