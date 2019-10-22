import React from 'react';

export interface IAvatar {
    imgURL: string,
}

const Avatar = (props: IAvatar) => {
    return <img src={props.imgURL}/>
};

export default Avatar