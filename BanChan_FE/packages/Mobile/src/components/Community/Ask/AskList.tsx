import React from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../Header";

const notifications = [
  {
    id: 1,
    title: "주차장 불법 주차 ",
    date: "2024.07.17",
    likes: 181,
  },
  {
    id: 2,
    title: "음식물 쓰레기 처리장 냄새",
    date: "2024.07.08",
    likes: 163,
  },
  {
    id: 3,
    title: "불법 주차 단속 부탁드립니다.",
    date: "2024.06.28",
    likes: 121,
  },
  {
    id: 4,
    title: "층간소음 문제 건의합니다. ",
    date: "2024.06.28",
    likes: 121,
  },
  {
    id: 5,
    title: "주차장 불법 주차 ",
    date: "2024.07.17",
    likes: 181,
  },
  {
    id: 6,
    title: "음식물 쓰레기 처리장 냄새",
    date: "2024.07.08",
    likes: 163,
  },
];

const AskList: React.FC = () => {
  const navigate = useNavigate();

  const goToDetail = () => {
    navigate("/m/community/notice/detail");
  };

  const goToWrite = () => {
    navigate("/m/community/ask/write");
  };

  return (
    <div className="min-h-screen">
      <Header>건의함</Header>
      <div className="max-w-md mx-auto p-4 mt-5">
        <div className="flex justify-start items-center mb-4">
          <div className="text-gray-500 mr-2">
            <i className="fas fa-bell"></i>
          </div>
          <div className="text-gray-500">총 57건의 건의를 하셨습니다.</div>
        </div>
        <div className="relative mb-4 flex justify-end">
          <button
            onClick={goToWrite}
            className="py-2 px-4 bg-blue-500 rounded-xl"
          >
            글 쓰기
          </button>
          {/* <input
            type="text"
            placeholder="검색"
            className="w-full border rounded-md py-2 pl-10 pr-4 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <i className="fas fa-search absolute top-3 left-3 text-gray-400"></i> */}
        </div>
        <div className="border-b-4 border-black"></div>

        <div>
          {notifications.map((item) => (
            <div key={item.id} className="border-b py-3">
              <div className="flex flex-col justify-between items-start">
                <div onClick={goToDetail} className="font-semibold text-lg">
                  {item.title}
                </div>
                <div className="text-gray-500 text-sm">{item.date}</div>
              </div>
              {/* <div className="text-gray-500 text-sm flex items-center mt-2">
                <i className="fas fa-thumbs-up mr-2"></i>
                {item.likes}
              </div> */}
            </div>
          ))}
        </div>
        <div className="flex flex-col justify-between mt-4">
          {/* <div className="flex space-x-2 justify-end">
            <button className="bg-blue-100 text-blue-500 px-4 py-2 rounded-md">
              작성일
            </button>
            <button className="bg-orange-100 text-orange-500 px-4 py-2 rounded-md">
              추천순
            </button>
          </div> */}
          {/* 페이징처리 코드 알아서 넣으면 됨 !  */}
          <div className="flex justify-center items-center space-x-2">
            <button className="px-4 py-2 rounded-md">1</button>
            <button className="px-4 py-2 rounded-md">2</button>
            <button className="px-4 py-2 rounded-md">3</button>
            <span className="px-4 py-2">...</span>
            <button className="px-4 py-2 rounded-md">12</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AskList;