var exec = require('cordova/exec');

var currentMedia = {};

function PlayStream() { 
	console.log("PlayStream.js: is created");
}

var PlayStream = function(src, successCallback, errorCallback){
	currentMedia = this;
    this.src = src;
    this.successCallback = successCallback;
    this.errorCallback = errorCallback;
    exec(this.successCallback, this.errorCallback, "PlayStream", "create", [this.src]);
};

// Media states
PlayStream.MEDIA_NONE = 0;
PlayStream.MEDIA_STARTING = 1;
PlayStream.MEDIA_RUNNING = 2;
PlayStream.MEDIA_PAUSED = 3;
PlayStream.MEDIA_STOPPED = 4;
PlayStream.MEDIA_MSG = ["None", "Starting", "Running", "Paused", "Stopped"];

/**
 * Start or resume playing audio file.
 */
PlayStream.prototype.play = function() {
    exec(null, this.errorCallback, "PlayStream", "startPlayingAudio", []);
};

/**
 * Stop playing audio file.
 */
PlayStream.prototype.stop = function() {
    exec(null, this.errorCallback, "PlayStream", "stopPlayingAudio", []);
};

/**
 * Release playing audio file.
 */
PlayStream.prototype.release = function() {
    exec(null, this.errorCallback, "PlayStream", "release", []);
};

//var PlayStream = new PlayStream(); 
module.exports = PlayStream;