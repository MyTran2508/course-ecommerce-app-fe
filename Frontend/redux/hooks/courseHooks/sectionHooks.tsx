import { Section } from "@/types/section.type";
import {
  useAddSectionMutation,
  useAddLectureMutation,
  useUpdateSectionByIdMutation,
} from "../../services/contentApi";

export function useSectionHooks() {
  const [
    addSection,
    { isLoading: isAddingSection, isSuccess: addSectionSuccess },
  ] = useAddSectionMutation();

  const [updateSection] = useUpdateSectionByIdMutation();

  const handleAddSection = async (newSection: Section) => {
    await addSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
      });
  };

  const handleUpdateSection = async (newSection: Section) => {
    await updateSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
      });
  };

  return {
    handleAddSection,
    handleUpdateSection,
  };
}
