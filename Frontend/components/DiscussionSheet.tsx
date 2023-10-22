import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import Image from "next/image";
import { AiFillWechat } from "react-icons/ai";
import Comment from "./Comment";

function DiscussionSheet() {
  const renderComment = () => {
    return (
      <div>
        <Comment />
        <Comment />
        <Comment />
      </div>
    );
  };
  return (
    <Sheet>
      <SheetTrigger
        asChild
        className="fixed bottom-0 right-1/4 mr-5 mb-5 rounded-3xl gap-1 text-orange-500"
      >
        <Button variant="outline">
          <AiFillWechat />
          Hỏi đáp
        </Button>
      </SheetTrigger>
      <SheetContent className="overflow-y-scroll h-[800px] w-5/12">
        <SheetHeader className="flex-start flex-col ml-2">
          <SheetTitle>54 Bình Luận</SheetTitle>
          <SheetDescription className="font-sans">
            (Hãy bình luận nếu có thắc mắc)
          </SheetDescription>
        </SheetHeader>
        <Input
          id="comment"
          placeholder="Bạn có thắc mắc gì"
          className="border-b-4 border-y-none mt-10"
        />
        <div className="mt-20">{renderComment()}</div>
      </SheetContent>
    </Sheet>
  );
}

export default DiscussionSheet;
