import { RoleType, User } from "@/types/user.type";
import { Checkbox } from "@/components/ui/checkbox";
import { FaRegCheckCircle, FaRegTimesCircle } from "react-icons/fa";
import ActionsCell from "@/components/Dialog/ActionCell";
import { ArrowUpDown, ChevronDown, MoreHorizontal } from "lucide-react";
import { ColumnDef } from "@tanstack/react-table";
import { Button } from "@/components/ui/button";
import { Course } from "@/types/course.type";
import FilterColumn from "./FilterColumn";
import { Order } from "@/types/order.type";
import { AssignmentHistory } from "@/types/assignment.type";
import { convertMillisToDateTime, formatTime } from "@/utils/function";
import { UserLog } from "@/types/userLog.type";
import ShowAll from "./ShowAll";

export const userColumns: ColumnDef<User>[] = [
  // {
  //   id: "select",
  //   header: ({ table }) => (
  //     <Checkbox
  //       checked={
  //         table.getIsAllPageRowsSelected() ||
  //         (table.getIsSomePageRowsSelected() && "indeterminate")
  //       }
  //       onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
  //       aria-label="Select all"
  //     />
  //   ),
  //   cell: ({ row }) => (
  //     <Checkbox
  //       checked={row.getIsSelected()}
  //       onCheckedChange={(value) => row.toggleSelected(!!value)}
  //       aria-label="Select row"
  //     />
  //   ),
  //   enableSorting: false,
  //   enableHiding: false,
  // },
  {
    accessorKey: "username",
    header: "Username",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("username")}</div>
    ),
  },
  {
    accessorKey: "email",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Email
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      );
    },
    cell: ({ row }) => <div className="lowercase">{row.getValue("email")}</div>,
  },
  {
    accessorKey: "roles",
    header: "Role",
    cell: ({ row }) => (
      <div className="capitalize">
        {(row.original.roles as RoleType[])[0].name}
      </div>
    ),
  },
  {
    accessorKey: "removed",
    header: "Active",
    cell: ({ row }) => (
      <div className="capitalize flex-start">
        {row.getValue("removed") ? (
          <FaRegTimesCircle className="text-lg text-red-500" />
        ) : (
          <FaRegCheckCircle className="text-lg text-green-500" />
        )}
      </div>
    ),
  },

  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => <ActionsCell user={row.original} />,
  },
];

export const courseColumns: ColumnDef<Course>[] = [
  // {
  //   id: "select",
  //   header: ({ table }) => (
  //     <Checkbox
  //       checked={
  //         table.getIsAllPageRowsSelected() ||
  //         (table.getIsSomePageRowsSelected() && "indeterminate")
  //       }
  //       onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
  //       aria-label="Select all"
  //     />
  //   ),
  //   cell: ({ row }) => (
  //     <Checkbox
  //       checked={row.getIsSelected()}
  //       onCheckedChange={(value) => row.toggleSelected(!!value)}
  //       aria-label="Select row"
  //     />
  //   ),
  //   enableSorting: false,
  //   enableHiding: false,
  // },
  {
    accessorKey: "name",
    header: "Name Course",
    cell: ({ row }) => <div className="capitalize">{row.getValue("name")}</div>,
  },
  {
    accessorKey: "isApproved",
    header: () => <FilterColumn name="isApproved" />,
    cell: ({ row }) => (
      <div className="capitalize ml-10">
        {row.getValue("isApproved") ? (
          <FaRegCheckCircle className="text-lg text-green-500" />
        ) : (
          <FaRegTimesCircle className="text-lg text-red-500" />
        )}
      </div>
    ),
  },
  {
    accessorKey: "isAwaitingApproval",
    header: () => <FilterColumn name="isAwaitingApproval" />,
    cell: ({ row }) => (
      <div className="capitalize ml-10">
        {row.getValue("isAwaitingApproval") ? (
          <FaRegCheckCircle className="text-lg text-green-500" />
        ) : (
          <FaRegTimesCircle className="text-lg text-red-500" />
        )}
      </div>
    ),
  },
  {
    accessorKey: "authorName",
    header: () => "Author",
    cell: ({ row }) => (
      <div className="italic">{row.getValue("authorName")}</div>
    ),
  },

  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => <ActionsCell course={row.original} />,
  },
];

export const billColumns: ColumnDef<Order>[] = [
  {
    accessorKey: "user",
    header: "Username",
    cell: ({ row }) => (
      <div className="capitalize">{(row.original.user as User).username}</div>
    ),
  },
  {
    accessorKey: "totalPrice",
    header: "Total Price",
    cell: ({ row }) => (
      <div className="lowercase">{row.getValue("totalPrice")}</div>
    ),
  },
  {
    accessorKey: "id",
    header: "ID",
    cell: ({ row }) => <div className="capitalize">{row.getValue("id")}</div>,
  },
  {
    accessorKey: "created",
    header: "Order Day",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("created")}</div>
    ),
  },

  {
    accessorKey: "shippingMethod",
    header: "Payment Method",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("shippingMethod")}</div>
    ),
  },

  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => <ActionsCell bill={row.original.orderItems} />,
  },
];

export const assignmentHistoryColumns: ColumnDef<AssignmentHistory>[] = [
  {
    accessorKey: "username",
    header: "Username",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("username")}</div>
    ),
  },
  {
    accessorKey: "score",
    header: "Score",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("score") || <i>None</i>}</div>
    ),
  },
  // {
  //   accessorKey: "evaluation",
  //   header: "Evaluation",
  //   cell: ({ row }) => (
  //     <div className="capitalize">
  //       {(() => {
  //         try {
  //           const evaluation = row.getValue("evaluation");
  //           if (!evaluation)
  //             throw new Error("Evaluation is null or undefined.");
  //           const parsed = JSON.parse(evaluation as string);
  //           console.log(parsed?.blocks);
  //           return parsed?.Blocks || <i>None</i>;
  //         } catch (error) {
  //           return <i>None</i>;
  //         }
  //       })()}
  //     </div>
  //   ),
  // },
  {
    accessorKey: "originalNumber",
    header: "Original Number",
    cell: ({ row }) => (
      <div className="capitalize">
        {(row.getValue("originalNumber") as number) + 1}
      </div>
    ),
  },

  {
    accessorKey: "created",
    header: "Time Start",
    cell: ({ row }) => (
      <div className="capitalize">
        {convertMillisToDateTime(row.getValue("created") as number)}
      </div>
    ),
  },

  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => <ActionsCell assignmentHistory={row.original} />,
  },
];

export const userLogColumns: ColumnDef<UserLog>[] = [
  {
    accessorKey: "userName",
    header: "Username",
    cell: ({ row }) => (
      <div className="capitalize">{row.getValue("userName")}</div>
    ),
  },
  {
    accessorKey: "actionObject",
    header: "Action Object",
    cell: ({ row }) => (
      <div className="capitalize">
        {row.getValue("actionObject") || <i>None</i>}
      </div>
    ),
  },
  {
    accessorKey: "actionName",
    header: "Action Name",
    cell: ({ row }) => (
      <div className="capitalize">
        {row.getValue("actionName") || <i>None</i>}
      </div>
    ),
  },
  {
    accessorKey: "actionKey",
    header: "Action Key",
    cell: ({ row }) => (
      <div className="capitalize">
        {row.getValue("actionKey") || <i>None</i>}
      </div>
    ),
  },
  {
    accessorKey: "description",
    header: "Description",
    cell: ({ row }) => <ShowAll row={row} />,
  },
  {
    accessorKey: "created",
    header: "Time",
    cell: ({ row }) => (
      <div className="capitalize">
        {convertMillisToDateTime(row.getValue("created") as number)}
      </div>
    ),
  },
];
