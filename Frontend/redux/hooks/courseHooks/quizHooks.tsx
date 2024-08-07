import { useAddExQuizMutation } from "@/redux/services/contentApi";
import {
  useAddQuestionMutation,
  useUpdateListQuestionMutation,
  useUpdateQuestionMutation,
} from "@/redux/services/quizApi";
import { ExQuiz, Question } from "@/types/section.type";
import { StatusCode, ToastStatus } from "@/utils/resources";
import showToast from "@/utils/showToast";

export function useExQuizHooks() {
  const [
    addExQuiz,
    { isLoading: isAddingExQuiz, isSuccess: addExQuizSuccess },
  ] = useAddExQuizMutation();

  const [addQuestion, { isLoading: isAddingQuestion }] =
    useAddQuestionMutation();

  const [updateListQuestion, { isLoading: isUpdatingListQuestion }] =
    useUpdateListQuestionMutation();

  const [updateQuestion] = useUpdateQuestionMutation();

  // const [getExQuizById, { data: exQuizData }] = useLazyGetExQuizByIdQuery();

  // const handleGetExQuizById = async (id: string) => {
  //   return getExQuizById(id).then((fulfilled) => {
  //     console.log(fulfilled);
  //     return fulfilled.data;
  //   });
  // };

  const handleAddExQuiz = async (lectureId: string, data: ExQuiz) => {
    await addExQuiz({ id: lectureId, data: data })
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
      });
  };

  const handleAddQuestion = async (exQuizId: string, data: Question) => {
    await addQuestion({ id: exQuizId, data: data })
      .unwrap()
      .then((fulfilled) => {
        if(fulfilled.statusCode == StatusCode.DATA_CONFLICT){
          showToast(ToastStatus.ERROR, "Câu hỏi đã tồn tại");
          return false;
        }else{
          return true;
        }
      });
  };

  const handleUpdateListQuestion = async (
    exQuizId: string,
    data: Question[]
  ) => {
    await updateListQuestion({ id: exQuizId, data: data })
      .unwrap()
      .then((fulfilled) => {
        if(fulfilled.statusCode == StatusCode.DATA_CONFLICT){
          showToast(ToastStatus.ERROR, "Câu hỏi đã tồn tại");
          return false;
        }else{
          return true;
        }
      });
  };

  const handleUpdateQuestion = async (data: Question) => {
    await updateQuestion(data)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
      });
  };

  return {
    handleAddExQuiz,
    handleAddQuestion,
    handleUpdateListQuestion,
    handleUpdateQuestion,
    // handleGetExQuizById,
  };
}
