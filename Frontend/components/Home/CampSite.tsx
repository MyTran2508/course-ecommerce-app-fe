import React from 'react'

export default function CampSite({ backgroundImage } : any) {
  return (
    <div className={`h-full w-full min-w-1100px ${backgroundImage} bg-cover 
    bg-no-repeat lg:rounded-r-5xl 2xl:rounded-5xl relative`}>
      <div className='absolute top-[20%] left-14 flex flex-col items-start bg-white p-8 w-[440px] gap-2'>
        <h1 className='font-bold text-4xl text-black'>Skills that drive you forward</h1>
        <p className='text-[#333]'>Technology and the world of work change fast — with us, you’re faster. Get the skills to achieve goals and stay competitive.</p>
        <button className='text-white bg-black p-2 font-bold'>Plan for organizations</button>
      </div>
    </div>
  )
}
