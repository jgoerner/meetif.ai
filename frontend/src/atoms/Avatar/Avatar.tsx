import React from 'react';

export interface IAvatar {
    url: string,
}

const Avatar = (props: IAvatar) => {
    return <img src={props.url}/>
};

export default Avatar