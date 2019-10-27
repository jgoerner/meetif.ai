import React from 'react';
import Styles from "./Avatar.module.scss";

export interface IAvatar {
    imgURL: string,
}

const Avatar = (props: IAvatar) => {
    return <img className={Styles.Avatar} src={props.imgURL}/>
};

export default Avatar