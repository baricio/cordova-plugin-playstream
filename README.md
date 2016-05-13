---
title: PlayStream
description: Play AAC+ and MP4 streamings.
---

# cordova-plugin-playstream

This plugin paly streaming audios into AAC+ and MP4 streamings using aacdecoder-android.

## Installation

    cordova plugin add cordova-plugin-playstream

    // you may also install directly from this repo
    cordova plugin add https://github.com/baricio/cordova-plugin-playstream.git

## Supported Platforms

- Android

## Use example

```
var url = 'streaming_url';

var my_media = new PlayStream(url,
    function (status){

        if(status === PlayStream.MEDIA_STOPPED){
            console.log('stopped');
        }

        if(status === PlayStream.MEDIA_STARTING){
            console.log('starting');
        }

        if(status === PlayStream.MEDIA_RUNNING){
            console.log('running');
        }

    },

    function (err) {
        alert(err);
    }
);

// Play audio
my_media.play();

```
