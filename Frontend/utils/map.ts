import { BiLogIn } from "react-icons/bi";
import { BsFillPenFill } from "react-icons/bs";
import { IconType } from "react-icons";
import { FcGoogle } from "react-icons/fc";
import { CiUser } from "react-icons/ci";
import {
  MdAlignHorizontalLeft,
  MdDriveFileRenameOutline,
  MdOutlineAccountCircle,
  MdOutlineEmail,
  MdOutlinePhone,
  MdOutlineSubtitles,
} from "react-icons/md";
import { FaMoneyBillWave } from "react-icons/fa";

export const iconMap: { [key: string]: IconType } = {
  BiLogIn: BiLogIn,
  BsFillPenFill: BsFillPenFill,
  FcGoogle: FcGoogle,

  //search course type
  CiUser: CiUser,
  MdDriveFileRenameOutline: MdDriveFileRenameOutline,
  MdOutlineSubtitles: MdOutlineSubtitles,
  MdOutlinePhone: MdOutlinePhone,
  MdOutlineEmail: MdOutlineEmail,
  MdOutlineAccountCircle: MdOutlineAccountCircle,
  MdAlignHorizontalLeft: MdAlignHorizontalLeft,
  FaMoneyBillWave: FaMoneyBillWave,
};
