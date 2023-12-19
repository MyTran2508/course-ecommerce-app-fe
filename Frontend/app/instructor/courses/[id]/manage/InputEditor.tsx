import {
  ContentState,
  EditorState,
  convertToRaw,
  convertFromRaw,
} from "draft-js";
import React, { useEffect, useState } from "react";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

function InputEditor() {
  const data = `{"blocks":[{"key":"dc7dv","text":"Trần Chí Mỹ","type":"unstyled","depth":0,"inlineStyleRanges":[{"offset":0,"length":11,"style":"BOLD"},{"offset":0,"length":11,"style":"ITALIC"}],"entityRanges":[],"data":{}}],"entityMap":{}}`;
  const convertData1 = JSON.parse(data);
  const raw = convertFromRaw(convertData1);
  const [text, setText] = useState<EditorState>(
    EditorState.createWithContent(raw)
  );

  const handleEditor = (text: any) => {
    setText(text);
  };

  useEffect(() => {
    if (text) {
      console.log(
        JSON.stringify(convertToRaw(text?.getCurrentContent() as ContentState))
      );
    }
  }, [text]);
  return (
    <div className="border-2 w-full">
      <Editor
        editorState={text}
        toolbarClassName="toolbarClassName"
        wrapperClassName="wrapperClassName"
        editorClassName="editorClassName"
        onEditorStateChange={handleEditor}
      />
    </div>
  );
}

export default InputEditor;
