
export interface ComboBoxType {
    id: string,
    name: string
}

export const Language: ComboBoxType[] = [
    {id: "0", name: "Vietnamese" },
    {id: "1", name: "English"},
]
export const Topic: ComboBoxType[] = [
    { id: "0", name: "Python" },
    { id: "1", name: "Java" },
    { id: "2", name: "C#" },
    { id: "3", name: "C++" },
    { id: "4", name: "ReactJS" },
    { id: "5", name: "Angular" },
    { id: "6", name: "VueJS" },
    { id: "7", name: "iSO Development" },
    { id: "8", name: "Android Development" },
    { id: "9", name: "React Native" },
    { id: "10", name: "Selenium WebDriver" },
    { id: "11", name: "Postman" },
    { id: "12", name: "JUnit" },
]

export const Level: ComboBoxType[] = [
    { id: "0", name: "Beginner" },
    { id: "1", name: "Intermediate" },
    { id: "2", name: "Expert" },
    { id: "3", name: "All Level" }
]