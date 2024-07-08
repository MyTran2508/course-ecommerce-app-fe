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
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
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
import { Action, ActionObject, Fields, ModuleName } from "@/utils/resources";
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
  const [action, setAction] = useState<string>("");
  const [actionKey, setActionKey] = useState<string>("");
  const [searchQuery, setSearchQuery] = useState<SearchRequest>({
    keyword: [null, null, null, null, null],
    isDecrease: true,
    pageIndex: 0,
    pageSize: 10,
  });

  useEffect(() => {
    if (startDate && endDate) {
      setSearchQuery((prevSearchQuery) => ({
        ...prevSearchQuery,
        pageIndex: 0,
        keyword: [
          keyword,
          startDate?.getTime().toString(),
          endDate?.getTime().toString(),
          action,
          actionKey,
        ],
      }));
    }
  }, [startDate, endDate]);

  useEffect(() => {
    getUserLogs(searchQuery);
  }, [searchQuery]);

  const handleSearch = () => {
    const newSearchRequest = {
      ...searchQuery,
      pageIndex: 0,
      keyword: [
        keyword,
        null,
        null,
        action !== "" ? action : null,
        actionKey !== "" ? actionKey : null,
      ],
    };
    setSearchQuery(newSearchRequest as any);
  };

  const getUserLogs = async (query: SearchRequest) => {
    await getUserLogBySearch(query as any)
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
            maxDate={endDate}
            className="border w-[100px] px-2 rounded-md"
            placeholderText="Start Date"
          />
        </div>
        <div className="flex gap-2">
          <p>End Date: </p>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date as Date)}
            minDate={startDate}
            className="border w-[100px] px-2 rounded-md"
            placeholderText="End Date"
          />
        </div>
      </div>
      <div className="flex my-2 gap-5 items-end">
        <div>
          <label className="font-bold mb-2">Username </label>
          <div className="flex gap-1 border rounded-sm p-1 overflow-x-auto custom-scrollbar bg-white">
            <div className="flex items-center py-1 w-full">
              <div className="relative w-full">
                <input
                  className={
                    "w-full placeholder:font-normal text-sm focus:outline-none min-w-[200px] px-2"
                  }
                  placeholder="Search by Username..."
                  // value={handleSetValueInput()}
                  onChange={(e) => setKeyword(e.target.value)}
                  autoComplete="off"
                ></input>
              </div>
            </div>
          </div>
        </div>
        <div>
          <label className="font-bold mb-2">Action Key</label>
          <div className="flex gap-1 border rounded-sm p-1 overflow-x-auto custom-scrollbar bg-white">
            <div className="flex items-center py-1 w-full">
              <div className="relative w-full">
                <input
                  className={
                    "w-full placeholder:font-normal text-sm focus:outline-none min-w-[200px] px-2"
                  }
                  placeholder="Search by ActionKey..."
                  // value={handleSetValueInput()}
                  onChange={(e) => setActionKey(e.target.value)}
                  autoComplete="off"
                ></input>
              </div>
            </div>
          </div>
        </div>
        <div>
          <label className="font-bold mb-2">Action</label>
          <Select defaultValue={action as string} onValueChange={setAction}>
            <SelectTrigger
              className={`disabled:opacity-1 disabled:cursor-default w-[200px] `}
            >
              <SelectValue placeholder="Select Action" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                {Object.keys(ActionObject).map((key) => (
                  <SelectItem value={key} key={key}>
                    {key}
                  </SelectItem>
                ))}
              </SelectGroup>
            </SelectContent>
          </Select>
        </div>
        <div
          className="right-0 flex justify-center items-center hover:cursor-pointer py-1 px-3 rounded-sm bg-gray-400 h-[44px]"
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
                    <TableHead
                      key={header.id}
                      className={`${
                        header.column.columnDef.header == Fields.Description ||
                        header.column.columnDef.header == Fields.Action_Key
                          ? "w-[30%]"
                          : ""
                      }`}
                    >
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

export default withAuth(UserLogPage, ModuleName.USER);
