"use client";
import {
  ColumnFiltersState,
  SortingState,
  VisibilityState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table";

import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useState, useEffect } from "react";
import {
  useFilterUserMutation,
  useGetAllUserQuery,
} from "@/redux/services/userApi";
import { userColumns, userLogColumns } from "@/components/Table/Columns";
import { SearchRequest } from "@/types/request.type";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { updateUser } from "@/redux/features/userSlice";
import SearchBarManufacturer from "@/components/SearchBar/SearchBarManufacturer";
import { Action, ModuleName } from "@/utils/resources";
import withAuth from "@/hoc/withAuth";
import { UserLog } from "@/types/userLog.type";
import { useGetUserLogMutation } from "@/redux/services/userLogApi";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { BiSearchAlt } from "react-icons/bi";
import { set } from "lodash";

function UserLogPage() {
  const [sorting, setSorting] = useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = useState({});
  const [userLogs, setUserLogs] = useState<UserLog[]>([]);
  const [totalPage, setTotalPage] = useState(0);
  const [getUserLogBySearch] = useGetUserLogMutation();
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [keyword, setKeyword] = useState<string>("");
  const [searchQuery, setSearchQuery] = useState<SearchRequest>({
    keyword: [""],
    sortBy: "",
    searchKeywordDtoList: [],
    isDecrease: true,
    pageIndex: 0,
    pageSize: 10,
  });

  useEffect(() => {
    if (startDate && endDate) {
      setSearchQuery((prevSearchQuery) => ({
        ...prevSearchQuery,
        startDate: startDate.getTime(),
        endDate: endDate.getTime(),
      }));
    }
  }, [startDate, endDate]);

  useEffect(() => {
    getUserLogs(searchQuery);
  }, [searchQuery]);

  const handleSearch = () => {
    const newSearchRequest = {
      ...searchQuery,
      keyword: [keyword],
    };
    setSearchQuery(newSearchRequest);
  };

  const getUserLogs = async (query: SearchRequest) => {
    await getUserLogBySearch(query)
      .unwrap()
      .then((fulfilled) => {
        setUserLogs(fulfilled.data as UserLog[]);
        setTotalPage(fulfilled.totalPages);
      });
  };

  const table = useReactTable({
    data: userLogs,
    columns: userLogColumns,
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    state: {
      sorting,
      columnFilters,
      columnVisibility,
      rowSelection,
      // pagination: paginate,
    },
  });

  return (
    <div className="w-full px-10">
      <div className="flex-end gap-5 z-20 mt-2 mr-3">
        <div className="flex gap-2">
          <p>Start Day: </p>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date as Date)}
            className="border w-[100px] px-2 rounded-md"
            placeholderText="Start Date"
          />
        </div>
        <div className="flex gap-2">
          <p>End Date: </p>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date as Date)}
            className="border w-[100px] px-2 rounded-md"
            placeholderText="End Date"
          />
        </div>
      </div>
      <div className="flex gap-1 my-2">
        <div className="flex gap-1 border rounded-sm p-1 overflow-x-auto custom-scrollbar bg-white w-1/3">
          <div className="flex items-center py-1 w-full">
            <div className="relative w-full">
              <input
                className={
                  "w-full placeholder:font-normal text-sm focus:outline-none min-w-[150px] px-2"
                }
                placeholder="Search..."
                // value={handleSetValueInput()}
                onChange={(e) => setKeyword(e.target.value)}
                autoComplete="off"
              ></input>
            </div>
          </div>
        </div>
        <div
          className="right-0 flex justify-center items-center hover:cursor-pointer py-1 px-2 rounded-sm bg-gray-400 h-[40px]"
          onClick={() => handleSearch()}
        >
          <BiSearchAlt className="text-2xl text-right" />
        </div>
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow
                  key={row.id}
                  data-state={row.getIsSelected() && "selected"}
                >
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext()
                      )}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell
                  colSpan={userColumns.length}
                  className="h-24 text-center"
                >
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      <div className="flex items-center justify-end space-x-2 py-4">
        <div className="flex-1 text-sm text-muted-foreground">
          {table.getFilteredSelectedRowModel().rows.length} of{" "}
          {table.getFilteredRowModel().rows.length} row(s) selected.
        </div>
        <div className="space-x-2 flex gap-1">
          <div className="flex-center text-sm text-muted-foreground">
            {searchQuery.pageIndex + 1} of {totalPage} page.
          </div>
          <Button
            variant="outline"
            size="sm"
            onClick={() =>
              setSearchQuery((prevSearchQuery) => ({
                ...prevSearchQuery,
                pageIndex: searchQuery.pageIndex - 1,
              }))
            }
            disabled={searchQuery.pageIndex === 0 ? true : false}
          >
            Previous
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() =>
              setSearchQuery((prevSearchQuery) => ({
                ...prevSearchQuery,
                pageIndex: searchQuery.pageIndex + 1,
              }))
            }
            disabled={searchQuery.pageIndex + 1 < totalPage ? false : true}
          >
            Next
          </Button>
        </div>
      </div>
    </div>
  );
}

export default UserLogPage;
