import React from 'react';
import Styles from "./Avatar.module.scss";

export interface IAvatar {
    imgURL: string,
    mini: boolean
}

const Avatar = (props: IAvatar) => {
    const styling = [Styles.Avatar];
    if (props.mini) {
        styling.push(Styles.MiniSized)
    } else {
        styling.push(Styles.NormalSized)
    }
    return <img className={styling.join(" ")} src={props.imgURL}/>
};

export default Avatar