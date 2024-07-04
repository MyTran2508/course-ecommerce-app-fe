import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import PersonalForm from "../Form/PersonalForm";
import { User } from "@/types/user.type";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { isPermissionGranted } from "@/utils/function";
import { ModuleName, PermissionName, Role } from "@/utils/resources";

interface UserDialogProps {
  isOpen: boolean;
  setIsOpen: (isOpen: boolean) => void;
  user: User;
}

function UserDialog(props: UserDialogProps) {
  const { isOpen, setIsOpen, user } = props;
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;
  return (
    <Dialog open={isOpen} onOpenChange={() => setIsOpen(false)}>
      <DialogContent className="w-[500px] flex-center flex-col">
        <DialogHeader>
          <DialogTitle>Chỉnh sửa user</DialogTitle>
        </DialogHeader>
        <PersonalForm
          userInfor={user}
          isAdmin={
            isPermissionGranted(
              roleDetail || [],
              PermissionName.CAN_UPDATE,
              ModuleName.USER
            ) || role?.name == Role.ADMIN
          }
        />
      </DialogContent>
    </Dialog>
  );
}

export default UserDialog;
