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
import DatePicker from "react-datepicker";
import { useState, useEffect } from "react";
import { billColumns, userColumns } from "@/components/Table/Columns";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import SearchBarManufacturer from "@/components/SearchBar/SearchBarManufacturer";
import { Action } from "@/utils/resources";
import { Order, SearchOrderDto } from "@/types/order.type";
import { useFilterOrderMutation } from "@/redux/services/orderApi";
import "react-datepicker/dist/react-datepicker.css";
import { convertToMilliseconds } from "@/utils/function";

function BillPage() {
  const dispatch = useAppDispatch();
  const [sorting, setSorting] = useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = useState({});
  const [billList, setBillList] = useState<Order[]>([]);
  const [totalPage, setTotalPage] = useState(0);
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [searchQuery, setSearchQuery] = useState<SearchOrderDto>({
    searchChooseList: [],
    sortBy: "created",
    searchKeywordDtoList: [],
    isDecrease: true,
    pageIndex: 0,
    pageSize: 10,
  });
  const [getBill, { data: bills, isSuccess: getBillSuccess }] =
    useFilterOrderMutation();

  useEffect(() => {
    getBill(searchQuery);
  }, [searchQuery]);

  useEffect(() => {
    if (getBillSuccess) {
      setBillList(bills?.data || []);
      setTotalPage((bills?.totalPages as number) || 0);
    }
  }, [bills, getBillSuccess]);

  useEffect(() => {
    if (startDate && endDate) {
      setSearchQuery((prevSearchQuery) => ({
        ...prevSearchQuery,
        startDate: startDate.getTime(),
        endDate: endDate.getTime(),
      }));
    }
  }, [startDate, endDate]);

  const table = useReactTable({
    data: billList,
    columns: billColumns,
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
      <div className="flex-end gap-2 z-20 mt-2">
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
      <div className="flex items-center py-4 w-full">
        <div className="flex gap-2 w-[600px]">
          <SearchBarManufacturer
            action={Action.SEARCH_BILL}
            setSearchQuery={setSearchQuery}
            startDate={startDate ? startDate.getTime() : null}
            endDate={endDate ? endDate.getTime() : null}
          />
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
            {(searchQuery.pageIndex as number) + 1} of {totalPage} page.
          </div>
          <Button
            variant="outline"
            size="sm"
            onClick={() =>
              setSearchQuery((prevSearchQuery) => ({
                ...prevSearchQuery,
                pageIndex: (searchQuery.pageIndex as number) - 1,
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
                pageIndex: (searchQuery.pageIndex as number) + 1,
              }))
            }
            disabled={
              (searchQuery.pageIndex as number) + 1 < totalPage ? false : true
            }
          >
            Next
          </Button>
        </div>
      </div>
    </div>
  );
}
export default BillPage;
