import React from 'react'

export default function PartnerBanner() {
  return (
    <div className='flex flex-col items-center justify-center gap-10 w-full mx-auto py-20'>
      <h4 className='text-gray-400 text-center text-lg'>
        Trusted by over 15,000 companies and millions of learners around the world
      </h4>
      <div className='flex flex-grow items-center justify-between gap-20'>
        <div>
          <img src="./samsung_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./citi_logo.svg" />
        </div>
        <div>
          <img src="./hewlett_packard_enterprise_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./procter_gamble_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./vimeo_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./cisco_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./volkswagen_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
        <div>
          <img src="./ericsson_logo.svg" alt="Volkswagen logo gray and white logo" />
        </div>
      </div>
    </div>
  )
}
