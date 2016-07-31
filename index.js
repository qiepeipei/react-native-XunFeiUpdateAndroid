/**
 * Created by qiepeipei on 16/7/21.
 */
'use strict';
import React, { PropTypes, Component } from 'react';
import {
    DeviceEventEmitter,
    NativeModules,
    Platform,
} from 'react-native'

var update = NativeModules.XunFeiUpdate;
class Update{
    
    constructor (config) {

        if(typeof config.DebugMode === 'undefined'){
            config.DebugMode = false;
        }

        if(typeof config.NotificationType === 'undefined'){
            config.NotificationType = 2;
        }

        if(typeof config.UpdateType === 'undefined'){

            config.UpdateType = 1;

        }

        if(typeof config.DebugMode != 'boolean'){

            throw 'DebugMode必须为bool类型';
        }

        if(typeof config.NotificationType != 'number'){

            throw 'NotificationType必须为int类型';
        }

        if(typeof config.UpdateType != 'number'){

            throw 'UpdateType必须为int类型';
        }

        if(parseInt(config.NotificationType) != config.NotificationType){

            throw 'NotificationType参数有误';
        }

        if(parseInt(config.UpdateType) != config.UpdateType){

            throw 'UpdateType参数有误';
        }

        this.updates = {};

        //接收 socketEvent事件
        this.deviceEventSubscription = DeviceEventEmitter.addListener(
            'updateEvent', this._updateEvent.bind(this)
        );



        // 初始化socket对象
        update.initialise(config);

    }

    _updateEvent(event){

        this.updates(event);

    }

    updateEvent(updates){

        this.updates = updates;

    }





}

module.exports = Update;
