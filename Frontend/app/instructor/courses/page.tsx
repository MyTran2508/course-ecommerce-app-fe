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
import { ChevronDown } from "lucide-react";

import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useState, useEffect } from "react";
import { courseColumns } from "@/components/Table/Columns";
import {
  useFilterCourseAdminMutation,
  useGetAllCourseQuery,
} from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { SearchRequest } from "@/types/request.type";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { updateCourse } from "@/redux/features/courseSlice";
import InstructorNavbar from "../Navbar";
import { Action, ModuleName } from "@/utils/resources";
import withAuth from "@/hoc/withAuth";
import SearchBarManufacturer from "@/components/SearchBar/SearchBarManufacturer";
import withAuthManager from "@/hoc/withAuthManager";

function InstructorCourses() {
  const dispatch = useAppDispatch();
  const [sorting, setSorting] = useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = useState({});
  const [courseList, setCourseList] = useState<Course[]>([]);
  const [totalPage, setTotalPage] = useState(0);
  const [getCourseByKeyword] = useFilterCourseAdminMutation();
  const [searchQuery, setSearchQuery] = useState<SearchRequest>({
    keyword: [],
    searchChooseList: [
      {
        keyword: "True",
        keywordType: 5,
      },
    ],
    searchKeywordDtoList: [],
    sortBy: "created",
    isDecrease: true,
    pageIndex: 0,
    pageSize: 10,
  });
  const isUpdateCourse = useAppSelector(
    (state) => state.courseReducer.updateCourse
  );

  const getCourseList = async (query: SearchRequest) => {
    await getCourseByKeyword(query)
      .unwrap()
      .then((fulfilled) => {
        setCourseList(fulfilled.data as Course[]);
        setTotalPage(fulfilled.totalPages);
      });
  };

  useEffect(() => {
    getCourseList(searchQuery);
  }, []);

  useEffect(() => {
    getCourseList(searchQuery);
  }, [searchQuery]);

  useEffect(() => {
    if (isUpdateCourse) {
      getCourseList(searchQuery);
      dispatch(updateCourse());
    }
  }, [isUpdateCourse]);

  const table = useReactTable({
    data: courseList,
    columns: courseColumns,
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
    },
  });

  return (
    <div>
      <InstructorNavbar />
      <div className="w-full px-10">
        <div className="flex items-center py-4">
          <div className="flex gap-2 w-[600px]">
            <SearchBarManufacturer
              action={Action.SEARCH_COURSE_ADMIN}
              setSearchQuery={setSearchQuery}
            />
          </div>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" className="ml-auto">
                Columns <ChevronDown className="ml-2 h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              {table
                .getAllColumns()
                .filter((column) => column.getCanHide())
                .map((column) => {
                  return (
                    <DropdownMenuCheckboxItem
                      key={column.id}
                      className="capitalize"
                      checked={column.getIsVisible()}
                      onCheckedChange={(value) =>
                        column.toggleVisibility(!!value)
                      }
                    >
                      {column.id}
                    </DropdownMenuCheckboxItem>
                  );
                })}
            </DropdownMenuContent>
          </DropdownMenu>
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
                    colSpan={courseColumns.length}
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
    </div>
  );
}
export default withAuthManager(InstructorCourses, ModuleName.COURSE_MANAGER);
