export interface ComboBoxType {
  id?: string;
  name?: string;
}

export const Language: ComboBoxType[] = [
  { id: "0", name: "Vietnamese" },
  { id: "1", name: "English" },
];

export const Price: ComboBoxType[] = [
  { id: "Paid", name: "Paid" },
  { id: "Free", name: "Free" },
];
export const Topic: ComboBoxType[] = [
  { id: "0", name: "Python" },
  { id: "1", name: "Java" },
  { id: "2", name: "HTML" },
  { id: "3", name: "C++" },
  { id: "4", name: "ReactJS" },
  { id: "5", name: "Angular" },
  { id: "6", name: "Android Development" },
  { id: "7", name: "NodeJS" },
];

export const Level: ComboBoxType[] = [
  { id: "0", name: "Beginner" },
  { id: "1", name: "Intermediate" },
  { id: "2", name: "Expert" },
  { id: "3", name: "All Level" },
];
export const VideoDuration: ComboBoxType[] = [
  { id: "0", name: "0 - 1 hours" },
  { id: "1", name: "1 - 3 hours" },
  { id: "2", name: "3 - 6 hours" },
  { id: "3", name: "6 - 17 hours" },
  { id: "4", name: "17 - max hours" },
];

export const Rating: ComboBoxType[] = [
  { id: "0", name: "4.5" },
  { id: "1", name: "4" },
  { id: "2", name: "3.5" },
  { id: "3", name: "3" },
];

export interface KeywordTypeSearchType {
  id?: string;
  name?: string;
  icon?: string;
}

export const KeywordTypeSearchCourse: KeywordTypeSearchType[] = [
  { id: "0", name: "Name", icon: "MdDriveFileRenameOutline" },
  { id: "1", name: "Author", icon: "CiUser" },
  { id: "2", name: "Subtitle", icon: "MdOutlineSubtitles" },
];

export const ComparisonOperators: KeywordTypeSearchType[] = [
  { id: "0", name: "=" },
  { id: "1", name: ">=" },
  { id: "2", name: "<=" },
];

export const KeywordTypeSearchUser: KeywordTypeSearchType[] = [
  { id: "0", name: "Username", icon: "MdDriveFileRenameOutline" },
  { id: "1", name: "Email", icon: "MdOutlineEmail" },
  { id: "2", name: "Full Name", icon: "MdOutlineAccountCircle" },
  { id: "3", name: "Telephone", icon: "MdOutlinePhone" },
  { id: "4", name: "All", icon: "MdAlignHorizontalLeft" },
];

export const KeywordTypeSearchBill: KeywordTypeSearchType[] = [
  { id: "0", name: "Username", icon: "MdDriveFileRenameOutline" },
  { id: "1", name: "Price", icon: "FaMoneyBillWave" },
];

export const KeywordTypeSearchAssignmentHistory: KeywordTypeSearchType[] = [
  { id: "0", name: "Username", icon: "MdDriveFileRenameOutline" },
  { id: "1", name: "LectureName", icon: "MdOutlineEmail" },
];
