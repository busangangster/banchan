import React, { useEffect } from "react";
import { ControlPanelsProps } from "../../Types";

const ControlPanels: React.FC<ControlPanelsProps> = ({
  // onChatToggle,
  activeIcons,
  handleButtonClick,
}) => {
  useEffect(() => {
    console.log(activeIcons);
  }, [activeIcons]);
  return (
    <div className="px-4 flex items-center mt-10">
      <div className="flex space-x-8 ml-[210px]">
        <button
          className={`bg-gray-800 text-white flex items-center px-4 py-2 rounded-full ${
            !activeIcons.mic ? "text-customRed" : "text-white"
          }`}
          onClick={() => handleButtonClick("mic")}
        >
          <span
            className={`material-symbols-outlined ${
              !activeIcons.mic ? "text-customRed" : "text-white"
            }`}
          >
            {!activeIcons.mic ? "mic_off" : "mic"}
          </span>
        </button>
        <button
          className={`bg-gray-800 text-white flex items-center px-4 py-2 rounded-full ${
            !activeIcons.videocam ? "text-customRed" : "text-white"
          }`}
          onClick={() => handleButtonClick("videocam")}
        >
          <span
            className={`material-symbols-outlined ${
              !activeIcons.videocam ? "text-customRed" : "text-white"
            }`}
          >
            {!activeIcons.videocam ? "videocam_off" : "videocam"}
          </span>
        </button>

        <button
          className={`bg-gray-800 text-white flex items-center px-4 py-2 rounded-full ${
            activeIcons.headset_mic ? "text-customRed" : "text-white"
          }`}
          onClick={() => handleButtonClick("headset_mic")}
        >
          <span
            className={`material-symbols-outlined ${
              activeIcons.headset_mic ? "text-customRed" : "text-white"
            }`}
          >
            {activeIcons.headset_mic ? "headset_off" : "headset_mic"}
          </span>
        </button>
        <button
          className="bg-red-500 text-white flex items-center px-4 py-2 rounded-full"
          onClick={() => handleButtonClick("exit_to_app")}
        >
          <span className={`material-symbols-outlined`}>exit_to_app</span>
          <span className="ml-2">회의 종료하기</span>
        </button>
      </div>

      {/* <div className="flex space-x-10 ml-[100px]">
        <span className="cursor-pointer material-symbols-outlined text-white">
          book
        </span>
        <span className="cursor-pointer material-symbols-outlined text-white">
          group
        </span>
        <span
          className={`cursor-pointer material-symbols-outlined ${
            activeIcons.chat_bubble ? "text-blue-500" : "text-white"
          }`}
          onClick={() => handleButtonClick("chat_bubble")}
        >
          chat_bubble
        </span>
        <div className="relative">
          <span className="cursor-pointer material-symbols-outlined text-yellow-500">
            notifications
          </span>
          <span className="absolute top-0 right-0 bg-red-500 text-white rounded-full text-xs w-4 h-4 flex items-center justify-center">
            1
          </span>
        </div>
      </div> */}
    </div>
  );
};

export default ControlPanels;