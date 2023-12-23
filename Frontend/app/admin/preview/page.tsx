"use client";
import React, { Fragment, useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { CategoryScale } from "chart.js";
import {
  useLazyMonthlySalesQuery,
  useLazySalesSamePeriodQuery,
  useMonthlySalesQuery,
} from "@/redux/services/orderApi";
import {
  useGetTotalApprovedCourseMutation,
  useLazySaleByTopicsQuery,
  useLazySalesSamePeriodByTopicsQuery,
} from "@/redux/services/courseApi";
ChartJS.register(CategoryScale);

const getRandomColor = () => {
  const r = Math.floor(Math.random() * 256);
  const g = Math.floor(Math.random() * 256);
  const b = Math.floor(Math.random() * 256);
  return `${r}, ${g}, ${b}`;
};
export interface MonthlySale {
  month: number;
  total: number;
}
export interface SalesSamePeriodByTopics {
  topicId: string;
  topicName: string;
  targetYearTotal: number;
  previousYearTotal: number;
}
export interface SalesSamePeriod {
  month: number;
  targetYearTotal: number;
  previousYearTotal: number;
}
export interface SalesByTopic {
  topicId: string;
  topicName: string;
  totalPrice: number;
}
function OverviewPage() {
  const [selectedYear, setSelectedYear] = useState("");
  const [monthlySale, setMonthlySale] = useState<MonthlySale[] | []>([]);
  const [salesByTopic, setSalesByTopic] = useState<SalesByTopic[] | []>([]);
  const [salesSamePeriodByTopics, setSalesSamePeriodByTopics] = useState<
    SalesSamePeriodByTopics[] | []
  >([]);
  const [salesSamePeriod, setSalesSamePeriod] = useState<
    SalesSamePeriod[] | []
  >([]);
  const [totalApproveCourse, setTotalApproveCourse] = useState(0);

  const [
    getMonthlySale,
    { data: monthLySaleData, isSuccess: isGetMonthlySaleSuccess },
  ] = useLazyMonthlySalesQuery();
  const [
    getSalesSamePeriod,
    { data: salesSamePeriodData, isSuccess: isGetSalesSamePeriodSuccess },
  ] = useLazySalesSamePeriodQuery();

  const [
    getSalesSamePeriodByTopics,
    {
      data: samePeriodByTopicsData,
      isSuccess: isGetSalesSamePeriodByTopicsSuccess,
    },
  ] = useLazySalesSamePeriodByTopicsQuery();
  const [
    getSalesByTopic,
    { data: salesByTopicData, isSuccess: isGetSalesByTopicSuccess },
  ] = useLazySaleByTopicsQuery();
  const [getTotalApproveCourse] = useGetTotalApprovedCourseMutation();

  const handleGetTotalApproveCourse = async () => {
    await getTotalApproveCourse({ targetYear: parseInt(selectedYear) })
      .unwrap()
      .then((fulfilled) => {
        setTotalApproveCourse(fulfilled.data as number);
      });
  };

  useEffect(() => {
    getMonthlySale(parseInt(selectedYear));
    getSalesByTopic(parseInt(selectedYear));
    getSalesSamePeriod(parseInt(selectedYear));
    getSalesSamePeriodByTopics(parseInt(selectedYear));
    handleGetTotalApproveCourse();
  }, [selectedYear]);

  useEffect(() => {
    if (isGetMonthlySaleSuccess) {
      setMonthlySale(monthLySaleData?.data as MonthlySale[]);
    }
    if (isGetSalesByTopicSuccess) {
      console.log(salesByTopicData?.data as SalesByTopic[]);
      setSalesByTopic(salesByTopicData?.data as SalesByTopic[]);
    }
    if (isGetSalesSamePeriodByTopicsSuccess) {
      setSalesSamePeriodByTopics(
        samePeriodByTopicsData?.data as SalesSamePeriodByTopics[]
      );
    }
    if (isGetSalesSamePeriodSuccess) {
      setSalesSamePeriod(salesSamePeriodData?.data as SalesSamePeriod[]);
    }
  }, [
    salesByTopicData,
    monthLySaleData,
    samePeriodByTopicsData,
    salesSamePeriodData,
  ]);

  const renderData = (
    data: string[][],
    column: number,
    label_x: string[],
    label_y: string[]
  ) => {
    const datasets = [];

    for (let i = 0; i < column; i++) {
      datasets.push({
        label: `${label_y[i]}`,
        data: data[i],
        backgroundColor: `rgba(${getRandomColor()}, 0.6)`,
      });
    }

    return {
      labels: label_x,
      datasets: datasets,
    };
  };
  const options = (title: string) => {
    return {
      plugins: {
        title: {
          display: true,
          text: title,
        },
      },
      responsive: true,
      maintainAspectRatio: false,
    };
  };
  const renderMonthlySales = () => {
    const label_x = [
      "Tháng 1",
      "Tháng 2",
      "Tháng 3",
      "Tháng 4",
      "Tháng 5",
      "Tháng 6",
      "Tháng 7",
      "Tháng 8",
      "Tháng 9",
      "Tháng 10",
      "Tháng 11",
      "Tháng 12",
    ];
    const label_y = ["Tổng"];
    const data: string[][] = [];
    data.push(monthlySale.map((item) => item.total.toString()));

    return renderData(data, 1, label_x, label_y);
  };

  const renderSalesSamePeriod = () => {
    const label_x = [
      "Tháng 1",
      "Tháng 2",
      "Tháng 3",
      "Tháng 4",
      "Tháng 5",
      "Tháng 6",
      "Tháng 7",
      "Tháng 8",
      "Tháng 9",
      "Tháng 10",
      "Tháng 11",
      "Tháng 12",
    ];
    const label_y = [
      `Tổng Năm ${selectedYear} `,
      `Tổng Năm ${parseInt(selectedYear) - 1}`,
    ];
    const data: string[][] = [];
    data.push(salesSamePeriod.map((item) => item.targetYearTotal.toString()));
    data.push(salesSamePeriod.map((item) => item.previousYearTotal.toString()));
    return renderData(data, 2, label_x, label_y);
  };
  const renderSalesByTopics = () => {
    console.log(salesByTopic);
    const label_x = salesByTopic.map((item) => item.topicName);
    const label_y = [`Tổng`];
    const data: string[][] = [];
    data.push(salesByTopic.map((item) => item.totalPrice.toString()));

    return renderData(data, 1, label_x, label_y);
  };
  const renderSalesSamePeriodByTopics = () => {
    const label_x = salesSamePeriodByTopics.map((item) => item.topicName);
    const label_y = [
      `Tổng Năm ${selectedYear} `,
      `Tổng Năm ${parseInt(selectedYear) - 1}`,
    ];
    const data: string[][] = [];
    data.push(
      salesSamePeriodByTopics.map((item) => item.targetYearTotal.toString())
    );
    data.push(
      salesSamePeriodByTopics.map((item) => item.previousYearTotal.toString())
    );

    return renderData(data, 2, label_x, label_y);
  };

  const renderYearOptions = () => {
    const options = [];
    for (let year = 2000; year <= 2050; year++) {
      options.push(
        <option key={year} value={year}>
          {year}
        </option>
      );
    }
    return options;
  };
  const handleYearChange = (event: any) => {
    setSelectedYear(event.target.value);
  };

  return (
    <div className="w-full px-10 mt-10  custom-scrollbar overflow-y-scroll h-[800px]">
      <div>
        <h2>Chọn năm</h2>
        <select
          value={selectedYear}
          onChange={handleYearChange}
          className="border-2 rounded-sm px-2 py-2 "
        >
          <option value="">Chọn năm </option>
          {renderYearOptions()}
        </select>
      </div>
      {selectedYear ? (
        <Fragment>
          <h5 className="flex-center text-2xl font-bold mb-10">
            Thống Kê Năm {selectedYear}
          </h5>
          <div className="block p-6 w-60 bg-red-300 border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 flex-center flex-col">
            <h5 className="mb-2 w-max text-xl font-bold tracking-tight text-gray-900 dark:text-white">
              Tổng Khóa Học
            </h5>
            <div className="font-normal text-gray-700 dark:text-gray-400 text-2xl">
              {totalApproveCourse}
            </div>
          </div>

          <div className="my-5 text-2xl font-bold">Doanh Thu Theo Tháng</div>
          <div className="flex gap-5">
            <div className="w-1/2 h-[400px] border my-5 px-2 py-2 rounded-sm flex gap-5">
              <Bar data={renderMonthlySales()} options={options("Doanh Số ")} />
            </div>
            <div className="w-1/2 h-[400px] border my-5 px-2 py-2 rounded-sm flex gap-5">
              <Bar
                data={renderSalesSamePeriod()}
                options={options("Doanh Số Cùng Kỳ")}
              />
            </div>
          </div>
          <div className="my-5 text-2xl font-bold">Doanh Thu Theo Chủ Đề</div>
          <div className="gap-5">
            <div className="w-full h-[400px] border my-5 px-2 py-2 rounded-sm flex gap-5">
              <Bar
                data={renderSalesByTopics()}
                options={options("Doanh Số ")}
              />
            </div>
            <div className="w-full h-[400px] border my-5 px-2 py-2 rounded-sm flex gap-5">
              <Bar
                data={renderSalesSamePeriodByTopics()}
                options={options("Doanh Số Cùng Kỳ")}
              />
            </div>
          </div>
        </Fragment>
      ) : null}
    </div>
  );
}

export default OverviewPage;
