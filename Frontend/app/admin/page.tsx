"use client";
import {
  Card,
  CardHeader,
  CardBody,
  Typography,
  Avatar,
  Chip,
  Tooltip,
  Progress,
} from "@material-tailwind/react";
import { AiFillUpCircle } from "react-icons/ai";
import { authorsTableData } from "./datatest/authors-table-data";
import { projectsTableData } from "./datatest/projects-table-data";
import { AuthorData, Member, ProjectData } from "./datatest/type";

export function Tables() {
  return (
    <div className="mt-12 mb-8 flex flex-col gap-12">
      <Card>
        <CardHeader variant="gradient" color="blue" className="mb-8 p-6">
          <Typography variant="h6" color="white">
            Authors Table
          </Typography>
        </CardHeader>
        <CardBody className="overflow-x-scroll px-0 pt-0 pb-2">
          <table className="w-full min-w-[640px] table-auto">
            <thead>
              <tr>
                {["author", "function", "status", "employed", ""].map((el) => (
                  <th
                    key={el}
                    className="border-b border-blue-gray-50 py-3 px-5 text-left"
                  >
                    <Typography
                      variant="small"
                      className="text-[11px] font-bold uppercase text-blue-gray-400"
                    >
                      {el}
                    </Typography>
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {authorsTableData.map((data: AuthorData, key: number) => {
                const className = `py-3 px-5 ${
                  key === authorsTableData.length - 1
                    ? ""
                    : "border-b border-blue-gray-50"
                }`;

                return (
                  <tr key={data.name}>
                    <td className={className}>
                      <div className="flex items-center gap-4">
                        <Avatar src={data.img} alt={data.name} size="sm" />
                        <div>
                          <Typography
                            variant="small"
                            color="blue-gray"
                            className="font-semibold"
                          >
                            {data.name}
                          </Typography>
                          <Typography className="text-xs font-normal text-blue-gray-500">
                            {data.email}
                          </Typography>
                        </div>
                      </div>
                    </td>
                    <td className={className}>
                      <Typography className="text-xs font-semibold text-blue-gray-600">
                        {data.job[0]}
                      </Typography>
                      <Typography className="text-xs font-normal text-blue-gray-500">
                        {data.job[1]}
                      </Typography>
                    </td>
                    <td className={className}>
                      <Chip
                        variant="gradient"
                        color={data.online ? "green" : "blue-gray"}
                        value={data.online ? "online" : "offline"}
                        className="py-0.5 px-2 text-[11px] font-medium"
                      />
                    </td>
                    <td className={className}>
                      <Typography className="text-xs font-semibold text-blue-gray-600">
                        {data.date}
                      </Typography>
                    </td>
                    <td className={className}>
                      <Typography
                        as="a"
                        href="#"
                        className="text-xs font-semibold text-blue-gray-600"
                      >
                        Edit
                      </Typography>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </CardBody>
      </Card>
      <Card>
        <CardHeader variant="gradient" color="blue" className="mb-8 p-6">
          <Typography variant="h6" color="white">
            Projects Table
          </Typography>
        </CardHeader>
        <CardBody className="overflow-x-scroll px-0 pt-0 pb-2">
          <table className="w-full min-w-[640px] table-auto">
            <thead>
              <tr>
                {["companies", "members", "budget", "completion", ""].map(
                  (el) => (
                    <th
                      key={el}
                      className="border-b border-blue-gray-50 py-3 px-5 text-left"
                    >
                      <Typography
                        variant="small"
                        className="text-[11px] font-bold uppercase text-blue-gray-400"
                      >
                        {el}
                      </Typography>
                    </th>
                  )
                )}
              </tr>
            </thead>
            <tbody>
              {projectsTableData.map((data: ProjectData, key: number) => {
                const className = `py-3 px-5 ${
                  key === projectsTableData.length - 1
                    ? ""
                    : "border-b border-blue-gray-50"
                }`;

                return (
                  <tr key={data.name}>
                    <td className={className}>
                      <div className="flex items-center gap-4">
                        <Avatar src={data.img} alt={data.name} size="sm" />
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-bold"
                        >
                          {data.name}
                        </Typography>
                      </div>
                    </td>
                    <td className={className}>
                      {data.members.map((member: Member, key: number) => (
                        <Tooltip key={member.name} content={member.name}>
                          <Avatar
                            src={member.img}
                            alt={member.name}
                            size="xs"
                            variant="circular"
                            className={`cursor-pointer border-2 border-white ${
                              key === 0 ? "" : "-ml-2.5"
                            }`}
                          />
                        </Tooltip>
                      ))}
                    </td>
                    <td className={className}>
                      <Typography
                        variant="small"
                        className="text-xs font-medium text-blue-gray-600"
                      >
                        {data.budget}
                      </Typography>
                    </td>
                    <td className={className}>
                      <div className="w-10/12">
                        <Typography
                          variant="small"
                          className="mb-1 block text-xs font-medium text-blue-gray-600"
                        >
                          {data.completion}%
                        </Typography>
                        <Progress
                          value={data.completion}
                          variant="gradient"
                          color={data.completion === 100 ? "green" : "blue"}
                          className="h-1"
                        />
                      </div>
                    </td>
                    <td className={className}>
                      <Typography
                        as="a"
                        href="#"
                        className="text-xs font-semibold text-blue-gray-600"
                      >
                        <AiFillUpCircle
                          strokeWidth={2}
                          className="h-5 w-5 text-inherit"
                        />
                      </Typography>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </CardBody>
      </Card>
    </div>
  );
}

export default Tables;
