import React from 'react';
import Avatar, { IAvatar } from "../../atoms/Avatar/Avatar";
import PersonDetail, { IPersonDetail } from "../../atoms/PersonDetail/PersonDetail";
import styles from "./PersonCard.module.css";

export type IPersonCard = IAvatar & IPersonDetail;


const PersonCard = (props: IPersonCard) => {
    let content = (
        <div className={styles.PersonCard}>
            <Avatar url={props.url}/>
            <PersonDetail {...props} />
        </div>
    );

    return content;
};

export default PersonCard;