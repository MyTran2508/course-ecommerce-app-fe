import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import InputEditor from "./InputEditor";

export function Message() {
  return (
    <div>
      <Dialog>
        <DialogTrigger asChild>
          <Button className="bg-red-600 text-white hover:bg-red-400">
            Không Chấp Nhận
          </Button>
        </DialogTrigger>
        <DialogContent className=" ">
          <DialogHeader>
            <DialogTitle>Lý Do Xét Duyệt Không Thành Công</DialogTitle>
          </DialogHeader>
          <InputEditor />
          <DialogFooter>
            <Button type="submit">Xác Nhận</Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
export default Message;
